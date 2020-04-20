package emmaTommy.TommyDataHandler.Actors;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.logging.log4j.LogManager;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.typed.PostStop;
import akka.pattern.Patterns;
import emmaTommy.DBAbstraction.Actors.DBClientAPI;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.AcquireDBLock;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.GetServizioByID;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.ReleaseDBLock;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.UpdateServizioByID;
import emmaTommy.DBAbstraction.ActorsMessages.Queries.WriteNewServizioByID;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.DBFailedToBeLocked;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.DBIsAlreadyLocked;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.DBLockAcquired;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.Reply;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.ReplyServizioById;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.ServizioByIDNotFound;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.UpdateServizioByIDFaillure;
import emmaTommy.DBAbstraction.ActorsMessages.Replies.UpdateServizioByIDSuccess;
import emmaTommy.TommyDataHandler.ActorsMessages.ServizioDataJSON;
import emmaTommy.TommyDataHandler.ActorsMessages.StartDataWriting;
import emmaTommy.TommyDataHandler.ActorsMessages.StopDataWriting;
import emmaTommy.TommyDataModel.Servizio;
import emmaTommy.TommyDataModel.TommyEnrichedJSON;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class TommyDataHandlerDataWriter extends AbstractActor {

	protected org.apache.logging.log4j.Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
	protected Boolean writeToStagingDB;
	protected Boolean activeStatus;
	protected String stagingDBActorName;
	protected ActorRef stagingDBActorRef;
	protected String persistenceDBActorName;
	protected ActorRef persistenceDBActorRef;
	protected int stagingDBAskTimeOutSecs;
	protected int stagingDBLockTimeOutSecs;
	protected int persistenceDBAskTimeOutSecs;
	protected int persistenceDBLockTimeOutSecs;
	protected String stagingDBPostingTableName;
	protected String stagingDBErrorTableName;
	protected String persistenceDBServiziCollectionName;
	
	protected DBClientAPI dbClient = new DBClientAPI(this.getClass().getSimpleName());
	
	
	
	public static Props props(String text, String confPath) {
        return Props.create(TommyDataHandlerDataWriter.class, text, confPath);
    }
	
	private TommyDataHandlerDataWriter(String confPath) {
		
		// Logger Method Name
		String method_name = "::TommyDataHandlerDataWriter(): ";
		
		// Define and Load Configuration File
 		Properties prop = new Properties();
 		logger.trace(method_name + "Loading Properties FileName: " + confPath);
 		FileInputStream fileStream = null;
 		try {
 			fileStream = new FileInputStream(confPath);
 		} catch (FileNotFoundException e) {
 			logger.fatal(method_name + e.getMessage());
 		}
 		try {
 		    prop.load(fileStream);
 		    logger.trace(method_name + prop.toString());
 		} catch (IOException e) {
 			logger.fatal(method_name + e.getMessage());
 		}
 		
 		// Read Properties
 		this.stagingDBActorName = prop.getProperty("stagingDBActorName");
 		this.persistenceDBActorName = prop.getProperty("persistenceDBActorName");
 		this.writeToStagingDB = (Integer.parseInt(prop.getProperty("writeToStagingDB")) == 1) ? (true) : (false);
 		this.stagingDBAskTimeOutSecs = Integer.parseInt(prop.getProperty("stagingDBAskTimeOutSecs"));
 		this.stagingDBLockTimeOutSecs = Integer.parseInt(prop.getProperty("stagingDBLockTimeOutSecs"));
 		this.persistenceDBAskTimeOutSecs = Integer.parseInt(prop.getProperty("persistenceDBAskTimeOutSecs"));
 		this.persistenceDBLockTimeOutSecs = Integer.parseInt(prop.getProperty("persistenceDBAskTimeOutSecs"));
 		this.stagingDBPostingTableName = prop.getProperty("stagingDBPostingTableName");
 		this.stagingDBErrorTableName = prop.getProperty("stagingDBErrorTableName");
 		this.persistenceDBServiziCollectionName = prop.getProperty("persistenceDBServiziCollectionName");
 		
 		// Set Active Status to false
 		this.activeStatus = false;
 		
 		
	}
	
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(StartDataWriting.class, this::onStart)
				.match(ServizioDataJSON.class, this::writeToDB)
				.match(StopDataWriting.class, this::onStop)
				.match(PostStop.class, signal -> onPostStop())
				.match(String.class, s -> {
					logger.info(this.getClass().getSimpleName() + " Received String message: {}", s);
	             })
				.matchAny(o -> logger.warn(this.getClass().getSimpleName() + " received unknown message"))
				.build();
	}
	
	protected void onStart(StartDataWriting start) {
		
		// Logger Method Name
		String method_name = "::onStart(): ";
		logger.info(method_name + "Received Start Consuming Event");
		
		// Active Status
		this.activeStatus = true;
		
	}
	
	protected void writeToDB (ServizioDataJSON servizioData) {
		String method_name = "::writeToDB(): ";
		logger.info(method_name + "Received new Servizio: " + servizioData.getID());
			
		if (servizioData.getValidData()) {
			
			// Create Enriched Servizio
			TommyEnrichedJSON servizioEnriched = new TommyEnrichedJSON(servizioData.getJSON());
			
			// Get servizio ID as String
			String servzioIDStr = Integer.toString(servizioData.getID());	
			
			try {
				
				// Create a Servizio Object from the Received JSON
				Servizio servizio = servizioEnriched.buildServizio();
			
				// Start Write/Update Operation
				Boolean lockAcquiredPersistenceDB = false;
			    try {
			    	
			    	// Get the Lock for the Persistence DB
					this.dbClient.acquireDBLockInfiniteLoop(this.getSelf(), this.persistenceDBActorRef, 
															this.persistenceDBAskTimeOutSecs, this.persistenceDBLockTimeOutSecs);
					lockAcquiredPersistenceDB = true;
					
					// Try to read the servizio from the Persistence DB		
					TommyEnrichedJSON servizioEnrichedDBPersistence = this.dbClient.getServizioByID(this.getSelf(), this.persistenceDBActorRef, 
																						  this.persistenceDBAskTimeOutSecs, 
																						  servzioIDStr, 
																						  this.persistenceDBServiziCollectionName);
					if (servizioEnrichedDBPersistence != null) { // Servizio was already in the Persistence DB
						
						logger.info(method_name + "Servizio " + servizioData.getID() + " was already present in the persistence DB");
						Servizio servizioDB = servizioEnrichedDBPersistence.buildServizio();
						if (servizioDB.equals(servizio)) { // Equals, Discard the New Data
							logger.info(method_name + "Servizio " + servizioData.getID() + " was already updated in the persistence DB");
						} else // Log Error, for manual update of the new data
						{
							logger.error(method_name + "Servizio " + servizioData.getID() + " was not updated in the persistence DB" + "\n"
									 	+ servizioData.getJSON());
						}
						
					} else { // Servizio wasn't in the Persistence DB
						Boolean lockAcquiredStagingDB = false;
						try {
						
							// Get the Lock for the Staging DB
							this.dbClient.acquireDBLockInfiniteLoop(this.getSelf(), this.stagingDBActorRef, 
																	this.stagingDBAskTimeOutSecs, this.stagingDBLockTimeOutSecs);	
							lockAcquiredStagingDB = true;
							
							// Try to read the servizio from the Staging DB - Error Section				
							TommyEnrichedJSON servizioEnrichedDBStagingError = this.dbClient.getServizioByID(this.getSelf(), this.stagingDBActorRef, 
																								 this.stagingDBAskTimeOutSecs, 
																								 servzioIDStr, this.stagingDBErrorTableName);
							if (servizioEnrichedDBStagingError != null) { // Servizio was already in the Staging DB - Error Section
								
								logger.info(method_name + "Servizio " + servizioData.getID() + " was already present in the staging DB - error section");
								Servizio servizioDB = servizioEnrichedDBStagingError.buildServizio();
								if (servizioDB.equals(servizio)) { // Equals, Discard the New Data
									logger.info(method_name + "Servizio " + servizioData.getID() + " was already updated in the staging DB - error section");
								} else { // Update the Servizio in the Staging DB - Error Serction
									logger.info(method_name + "Servizio " + servizioData.getID() + " was not updated in the staging DB - error section");
									this.dbClient.updateServizioByID(this.getSelf(), this.stagingDBActorRef, 
											this.stagingDBAskTimeOutSecs, 
											servzioIDStr, servizioEnriched.getJsonServizio(), this.stagingDBErrorTableName);
									logger.info(method_name + "Servizio " + servizioData.getID() + " Updated successfully in the staging DB - error section");
								}
								
							} else { // Servizio wasn't in the Staging DB - Error Section, will try the Posting Section
								
								// Try to read the servizio from the Staging DB - Posting Section				
								TommyEnrichedJSON servizioEnrichedDBStagingPosting = this.dbClient.getServizioByID(this.getSelf(), this.stagingDBActorRef, 
																									 this.stagingDBAskTimeOutSecs, 
																									 servzioIDStr, this.stagingDBPostingTableName);
								if (servizioEnrichedDBStagingPosting != null) { // Servizio was already in the Staging DB - Posting Section
									logger.info(method_name + "Servizio " + servizioData.getID() + " was already present in the staging DB - posting section");
									Servizio servizioDB = servizioEnrichedDBStagingPosting.buildServizio();
									if (servizioDB.equals(servizio)) { // Equals, Discard the New Data
										logger.info(method_name + "Servizio " + servizioData.getID() + " was already updated in the staging DB - posting section");
									} else { // Update the Servizio in the Staging DB - Error Serction
										logger.info(method_name + "Servizio " + servizioData.getID() + " was not updated in the staging DB - posting section");
										this.dbClient.updateServizioByID(this.getSelf(), this.stagingDBActorRef, 
												this.stagingDBAskTimeOutSecs, 
												servzioIDStr, servizioEnriched.getJsonServizio(), this.stagingDBPostingTableName);
										logger.info(method_name + "Servizio " + servizioData.getID() + " Updated successfully in the staging DB - posting section");
									}						
								} else { // Servizio wasn't in the Staging DB - Posting Section
									logger.info(method_name + "Servizio " + servizioData.getID() + " wasn't present in the staging DB - posting section");
									this.dbClient.writeNewServizioByID(this.getSelf(), this.stagingDBActorRef, 
																		 this.stagingDBAskTimeOutSecs, 
																		 servzioIDStr, servizioEnriched.getJsonServizio(), 
																		 this.stagingDBPostingTableName);
									logger.info(method_name + "Servizio " + servizioData.getID() + " Written successfully in the staging DB - posting section");
								}
								
							}
							
						} catch (Exception e) {
							logger.error(method_name + "Servizio " + servizioData.getID() + " failed operating on the staging DB: " + e.getMessage());	
						} finally {
							if (lockAcquiredStagingDB) 
								this.dbClient.releaseDBLock(this.getSelf(), this.stagingDBActorRef, this.stagingDBAskTimeOutSecs);
						}
						
					}
					
			    } catch (Exception e) {
					logger.error(method_name + "Servizio " + servizioData.getID() + " failed operating on the persistence DB: " + e.getMessage());
				}  finally {
					if (lockAcquiredPersistenceDB) 
						this.dbClient.releaseDBLock(this.getSelf(), this.persistenceDBActorRef, this.persistenceDBAskTimeOutSecs);
				}
			    
			} catch (Exception e) {
				logger.error(method_name + "Servizio " + servizioData.getID() + " failed to parse servizio JSON: " + e.getMessage());
			}
			
		} else {
			logger.error(method_name + "Servizio " + servizioData.getID() + " wasn't valid: " + servizioData.getErrorMsg());	
		}	
	}	
	
	protected void onStop(StopDataWriting stop) {
		
		// Logger Method Name
		String method_name = "::onStop(): ";
		logger.info(method_name + "Received stop DataWriting Event");
		
		// Active Status
		this.activeStatus = false;
		
	}
	
	protected void onPostStop() {
		String method_name = "::onPostStop(): ";
		logger.info(method_name + "Received Stop Event");		
		this.activeStatus = false;
		
	}
	
	
	
}
