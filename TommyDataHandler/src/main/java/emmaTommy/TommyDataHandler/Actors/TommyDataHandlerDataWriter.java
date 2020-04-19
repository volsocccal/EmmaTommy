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
	
	protected void writeToDB (ServizioDataJSON servizio) {
		String method_name = "::writeToDB(): ";
		logger.trace(method_name + "Received a new servizio msg");
		logger.info(method_name + "Received new Servizio: " + servizio.getID());
			
		if (servizio.getValidData()) {
			
			// Get the Lock for the Persistence DB
			int trialNumsLockPersistenceDB = 0;
			Boolean lockAcquiredPersistenceDB = false;
			while (!lockAcquiredPersistenceDB) {
				trialNumsLockPersistenceDB += 1;
				Future<Object> futurePersistenceLock = Patterns.ask(this.persistenceDBActorRef, new AcquireDBLock() , 1000);
			    try {
					Reply replyPersistenceLock = (Reply) Await.result(futurePersistenceLock, Duration.create(persistenceDBAskTimeOutSecs, TimeUnit.SECONDS));
					if (replyPersistenceLock instanceof DBLockAcquired) {
						lockAcquiredPersistenceDB = true;
						logger.trace(method_name + "Acquired Persistence DB Lock (Tentative " + trialNumsLockPersistenceDB + ")");	
					} else if (replyPersistenceLock instanceof DBIsAlreadyLocked) {
						throw new IllegalArgumentException("DB was already locked by " + ((DBIsAlreadyLocked) replyPersistenceLock).getLockOwner());
					} else if (replyPersistenceLock instanceof DBFailedToBeLocked) {
						throw new IllegalArgumentException(((DBFailedToBeLocked) replyPersistenceLock).getCause());
					} else if (replyPersistenceLock instanceof DBIsAlreadyLocked) {
						throw new IllegalArgumentException("DB was already locked by " + ((DBIsAlreadyLocked) replyPersistenceLock).getLockOwner());
					} else {
						throw new IllegalArgumentException("Unknown reply " + replyPersistenceLock.getReplyTypeName());
					}
				} catch (TimeoutException | InterruptedException | IllegalArgumentException  e) {
					logger.error(method_name + "Servizio " + servizio.getID() 
								+ " failed to Acquire persistence DB Lock (Tentative " + trialNumsLockPersistenceDB + "): " 
								+ e.getMessage());
					try {
						logger.error(method_name + "Will sleep for " + this.persistenceDBLockTimeOutSecs + " seconds");
						Thread.sleep(this.persistenceDBLockTimeOutSecs * 1000);
					} catch (InterruptedException e1) {
						logger.error(method_name + "Servizio " + servizio.getID() 
						+ " Interrupted my sleep : " 
						+ e1.getMessage());	
					}
				}
			}
			
			
			// Once the lock is acquired try to load the new JSON in the DB
		    try {
		    	
		    	// Get servizio ID as String
				String servzioIDStr = Integer.toString(servizio.getID());
				
				// Try to read the servizio from the Persistence DB		
				Future<Object> futurePersistenceGetServizio = Patterns.ask(this.persistenceDBActorRef, 
																			new GetServizioByID(servzioIDStr, this.persistenceDBServiziCollectionName), 
																			1000);
				Reply replyPersistenceGetServizio = (Reply) Await.result(futurePersistenceGetServizio, Duration.create(persistenceDBAskTimeOutSecs, TimeUnit.SECONDS));
				
				if (replyPersistenceGetServizio instanceof ReplyServizioById) { // Servizio was already in the Persistence DB
					logger.info(method_name + "Servizio " + servizio.getID() + " was already present in the persistence DB");
					String jsonDB = ((ReplyServizioById) replyPersistenceGetServizio).getServizio().getJsonServizio();
					if (jsonDB.compareTo(servizio.getJSON()) == 0) { // Equals, Discard the New Data
						logger.info(method_name + "Servizio " + servizio.getID() + " was already updated in the persistence DB");
					} else // Log Error, for manual update of the new data
					{
						logger.error(method_name + "Servizio " + servizio.getID() + " was not updated in the persistence DB" + "\n"
								 	+ servizio.getJSON());
					}
				} else if (replyPersistenceGetServizio instanceof ServizioByIDNotFound) {
					
					// Get the Lock for the Persistence DB
					int trialNumsLockStagingDB = 0;
					Boolean lockAcquiredStagingDB = false;
					while (!lockAcquiredStagingDB) {
						trialNumsLockPersistenceDB += 1;
						Future<Object> futureStagingLock = Patterns.ask(this.stagingDBActorRef, new AcquireDBLock() , 1000);
					    try {
							Reply replyStagingLock = (Reply) Await.result(futureStagingLock, Duration.create(stagingDBAskTimeOutSecs, TimeUnit.SECONDS));
							if (replyStagingLock instanceof DBLockAcquired) {
								lockAcquiredStagingDB = true;
								logger.trace(method_name + "Acquired Staging DB Lock (Tentative " + trialNumsLockStagingDB + ")");	
							} else if (replyStagingLock instanceof DBIsAlreadyLocked) {
								throw new IllegalArgumentException("DB was already locked by " + ((DBIsAlreadyLocked) replyStagingLock).getLockOwner());
							} else if (replyStagingLock instanceof DBFailedToBeLocked) {
								throw new IllegalArgumentException(((DBFailedToBeLocked) replyStagingLock).getCause());
							} else if (replyStagingLock instanceof DBIsAlreadyLocked) {
								throw new IllegalArgumentException("DB was already locked by " + ((DBIsAlreadyLocked) replyStagingLock).getLockOwner());
							} else {
								throw new IllegalArgumentException("Unknown reply " + replyStagingLock.getReplyTypeName());
							}
						} catch (TimeoutException | InterruptedException | IllegalArgumentException  e) {
							logger.error(method_name + "Servizio " + servizio.getID() 
										+ " failed to Acquire staging DB Lock (Tentative " + trialNumsLockStagingDB + "): " 
										+ e.getMessage());
							try {
								logger.error(method_name + "Will sleep for " + this.stagingDBLockTimeOutSecs + " seconds");
								Thread.sleep(this.persistenceDBLockTimeOutSecs * 1000);
							} catch (InterruptedException e1) {
								logger.error(method_name + "Servizio " + servizio.getID() 
								+ " Interrupted my sleep : " 
								+ e1.getMessage());	
							}
						}
					}
					
					// Once the lock is acquired try to load the new JSON in the DB
				    try {
						
						// Try to read the servizio from the Staging DB - Error Section		
						Future<Object> futureStagingErrorGetServizio = Patterns.ask(this.stagingDBActorRef, 
																				new GetServizioByID(servzioIDStr, this.stagingDBErrorTableName), 
																				1000);
						Reply replyStagingErrorGetServizio = (Reply) Await.result(futureStagingErrorGetServizio, Duration.create(stagingDBAskTimeOutSecs, TimeUnit.SECONDS));
						if (replyStagingErrorGetServizio instanceof ReplyServizioById) { // Servizio was already in the Staging DB - Error Section
							logger.info(method_name + "Servizio " + servizio.getID() + " was already present in the staging DB - error section");
							String jsonDB = ((ReplyServizioById) replyStagingErrorGetServizio).getServizio().getJsonServizio();
							if (jsonDB.compareTo(servizio.getJSON()) == 0) { // Equals, Discard the New Data
								logger.info(method_name + "Servizio " + servizio.getID() + " was already updated in the staging DB - error section");
							} else // Update
							{
								logger.info(method_name + "Servizio " + servizio.getID() + " was not updated in the staging DB - error section");
								try
								{
									Future<Object> futureStagingUpdateServizio = Patterns.ask(this.stagingDBActorRef, 
																								new UpdateServizioByID(servzioIDStr, new TommyEnrichedJSON(servizio.getJSON()), this.stagingDBErrorTableName), 
																								1000);
									Reply replyStagingUpdateServizio = (Reply) Await.result(futureStagingUpdateServizio, Duration.create(stagingDBAskTimeOutSecs, TimeUnit.SECONDS));
									if (replyStagingUpdateServizio instanceof UpdateServizioByIDSuccess) {
										logger.info(method_name + "Servizio " + servizio.getID() + " Updated successfully in the staging DB - error section");
									} else if (replyStagingUpdateServizio instanceof UpdateServizioByIDFaillure) {
										throw new IllegalArgumentException(((UpdateServizioByIDFaillure) replyStagingUpdateServizio).getCause());
									} else {
										throw new IllegalArgumentException("Unknown reply " + replyStagingUpdateServizio.getReplyTypeName());
									}								
								} catch (TimeoutException | InterruptedException | IllegalArgumentException e) {
									logger.error(method_name + "Servizio " + servizio.getID() 
												+ " failed to update the servizio in the staging DB - error section: " + e.getMessage());	
								}
							}
						} else if (replyStagingErrorGetServizio instanceof ServizioByIDNotFound) { // Servizio wasn't in the Staging DB - Error Section, will try the PostingSectionsection
							try {
						
								// Try to read the servizio from the Staging DB - Posting Section		
								Future<Object> futureStagingPostingGetServizio = Patterns.ask(this.stagingDBActorRef, 
																								new GetServizioByID(servzioIDStr, this.stagingDBPostingTableName), 
																								1000);
								Reply replyStagingPostingGetServizio = (Reply) Await.result(futureStagingPostingGetServizio, Duration.create(stagingDBAskTimeOutSecs, TimeUnit.SECONDS));
								if (replyStagingPostingGetServizio instanceof ReplyServizioById) { // Servizio was already in the Staging DB - Posting Section
									logger.info(method_name + "Servizio " + servizio.getID() + " was already present in the staging DB - posting section");
									String jsonDB = ((ReplyServizioById) replyStagingPostingGetServizio).getServizio().getJsonServizio();
									if (jsonDB.compareTo(servizio.getJSON()) == 0) { // Equals, Discard the New Data
										logger.info(method_name + "Servizio " + servizio.getID() + " was already updated in the staging DB - posting section");
									} else { // Update
										logger.info(method_name + "Servizio " + servizio.getID() + " was not updated in the staging DB - posting section");
										try
										{
											Future<Object> futureStagingUpdateServizio = Patterns.ask(this.stagingDBActorRef, 
																										new UpdateServizioByID(servzioIDStr, new TommyEnrichedJSON(servizio.getJSON()), this.stagingDBPostingTableName), 
																										1000);
											Reply replyStagingUpdateServizio = (Reply) Await.result(futureStagingUpdateServizio, Duration.create(stagingDBAskTimeOutSecs, TimeUnit.SECONDS));
											if (replyStagingUpdateServizio instanceof UpdateServizioByIDSuccess) {
												logger.info(method_name + "Servizio " + servizio.getID() + " Updated successfully in the staging DB - posting section");
											} else if (replyStagingUpdateServizio instanceof UpdateServizioByIDFaillure) {
												throw new IllegalArgumentException(((UpdateServizioByIDFaillure) replyStagingUpdateServizio).getCause());
											} else {
												throw new IllegalArgumentException("Unknown reply " + replyStagingUpdateServizio.getReplyTypeName());
											}								
										} catch (TimeoutException | InterruptedException | IllegalArgumentException e) {
											logger.error(method_name + "Servizio " + servizio.getID() 
														+ " failed to update the servizio in the staging DB - posting section: " + e.getMessage());	
										}
									}
								} else if (replyStagingPostingGetServizio instanceof ServizioByIDNotFound) { // Servizio wasn't in the Staging DB - Posting Section
									
									try
									{
										Future<Object> futureStagingWriteServizio = Patterns.ask(this.stagingDBActorRef, 
																									new WriteNewServizioByID(servzioIDStr, new TommyEnrichedJSON(servizio.getJSON()), this.stagingDBPostingTableName), 
																									1000);
										Reply replyStagingWriteServizio = (Reply) Await.result(futureStagingWriteServizio, Duration.create(stagingDBAskTimeOutSecs, TimeUnit.SECONDS));
										if (replyStagingWriteServizio instanceof UpdateServizioByIDSuccess) {
											logger.info(method_name + "Servizio " + servizio.getID() + " Written successfully in the staging DB - posting section");
										} else if (replyStagingWriteServizio instanceof UpdateServizioByIDFaillure) {
											throw new IllegalArgumentException(((UpdateServizioByIDFaillure) replyStagingWriteServizio).getCause());
										} else {
											throw new IllegalArgumentException("Unknown reply " + replyStagingWriteServizio.getReplyTypeName());
										}								
									} catch (TimeoutException | InterruptedException | IllegalArgumentException e) {
										logger.error(method_name + "Servizio " + servizio.getID() 
													+ " failed to write the servizio in the staging DB - posting section: " + e.getMessage());	
									}
								} else {
									throw new IllegalArgumentException("Unknown reply " + replyStagingPostingGetServizio.getReplyTypeName());
								}	
							} catch (TimeoutException | InterruptedException | IllegalArgumentException e) {
								logger.error(method_name + "Servizio " + servizio.getID() 
											+ " failed to  check if its present in the staging DB - posting section: " + e.getMessage());	
							}								
						} else {
							throw new IllegalArgumentException("Unknown reply " + replyStagingErrorGetServizio.getReplyTypeName());
						}	
				    } catch (TimeoutException | InterruptedException | IllegalArgumentException e) {
						logger.error(method_name + "Servizio " + servizio.getID() 
									+ " failed to check if its present in the staging DB - error section: " + e.getMessage());	
					} finally {
						if (lockAcquiredStagingDB) {
							Future<Object> futureReleaseStagingLock = Patterns.ask(this.stagingDBActorRef, new ReleaseDBLock() , 1000);
						    try {
								Reply replyReleaseStagingLock = (Reply) Await.result(futureReleaseStagingLock, Duration.create(stagingDBAskTimeOutSecs, TimeUnit.SECONDS));
						    } catch (TimeoutException | InterruptedException | IllegalArgumentException e) {
								logger.error(method_name + "Servizio " + servizio.getID() 
											+ " failed to release lock for the staging DB: " + e.getMessage());
						    }
						}
					}
						
				} else {
					throw new IllegalArgumentException ("Unknown reply type: " + replyPersistenceGetServizio.getReplyTypeName());
				}				
				
			} catch (TimeoutException | InterruptedException | IllegalArgumentException e) {
				logger.error(method_name + "Servizio " + servizio.getID() 
							+ " failed to if its present in the persistence DB: " + e.getMessage());	
			} finally {
				if (lockAcquiredPersistenceDB) {
					Future<Object> futureReleasePersistenceLock = Patterns.ask(this.persistenceDBActorRef, new ReleaseDBLock() , 1000);
				    try {
						Reply replyReleasePersistenceLock = (Reply) Await.result(futureReleasePersistenceLock, Duration.create(persistenceDBAskTimeOutSecs, TimeUnit.SECONDS));
				    } catch (TimeoutException | InterruptedException | IllegalArgumentException e) {
						logger.error(method_name + "Servizio " + servizio.getID() 
									+ " failed to release lock for the persistence DB: " + e.getMessage());
				    }
				}
			}
			
		} else {
			logger.error(method_name + "Servizio " + servizio.getID() + " wasn't valid: " + servizio.getErrorMsg());	
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
