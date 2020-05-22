package emmaTommy.TommyPoster.Actors;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Cancellable;
import akka.actor.Props;
import akka.actor.typed.PostStop;
import akka.pattern.Patterns;
import emmaTommy.DBAbstraction.Actors.DBClientAPI;
import emmaTommy.DBAbstraction.Actors.DBOperationFailedException;
import emmaTommy.TommyDataModel.TommyEnrichedJSON;
import emmaTommy.TommyPoster.ActorsMessages.PostData;
import emmaTommy.TommyPoster.ActorsMessages.PostDataResponse;
import emmaTommy.TommyPoster.ActorsMessages.PostOperationStart;
import emmaTommy.TommyPoster.ActorsMessages.StartPosting;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;

public class TommyPostHandler extends AbstractActor {
	
	protected org.apache.logging.log4j.Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
	protected String actorID = RandomStringUtils.randomAlphanumeric(10);
	
	protected String tommy_posthandler_conf;	
	protected String tommy_rest_service_refs;
	protected String tommy_db_conf;
	protected String startingServizioID;
	protected Boolean mockPost;
	protected Boolean writeOverPersistenceDB;
	protected int postOperationInterval;
	protected int tommyPostTimeout;
	protected Boolean save_post_json_to_disk;
	protected String post_json_folder;
	
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
	
	protected ActorRef TommyRestPosterActorRef;
	
	protected Cancellable cancellableJob;
	
	protected DBClientAPI dbClient = new DBClientAPI(this.getClass().getSimpleName());

	public static class Start {
        public final String name;

        public Start(String name) {
            this.name = name;
        }
    }
	
	public static Props props(String text, String confPath) {
        return Props.create(TommyPostHandler.class, text, confPath);
    }
	
	private TommyPostHandler(String confPath) {
        
        // Logger Method Name
        String method_name = "::TommyPoster(): ";
        
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
 		this.tommy_posthandler_conf = prop.getProperty("tommy_posthandler_conf");
 		this.tommy_rest_service_refs = prop.getProperty("tommy_rest_service_refs");
 		this.tommy_db_conf = prop.getProperty("tommy_db_conf");
 		this.startingServizioID = prop.getProperty("startingServizioID");
 		this.mockPost = (Integer.parseInt(prop.getProperty("mockPost")) == 1) ? (true) : (false);
 		this.writeOverPersistenceDB = (Integer.parseInt(prop.getProperty("writeOverPersistenceDB")) == 1) ? (true) : (false);
        this.postOperationInterval = Integer.parseInt(prop.getProperty("postOperationInterval"));
        this.tommyPostTimeout = Integer.parseInt(prop.getProperty("tommyPostTimeout"));
        this.save_post_json_to_disk = (Integer.parseInt(prop.getProperty("save_post_json_to_disk")) == 1) ? (true) : (false);
        this.post_json_folder = prop.getProperty("post_json_folder");
        
        // Read DB Properties
        Properties propDB = new Properties();
 		logger.trace(method_name + "Loading DB Properties FileName: " + this.tommy_db_conf);
 		FileInputStream fileStreamDB = null;
 		try {
 			fileStreamDB = new FileInputStream(this.tommy_db_conf);
 		} catch (FileNotFoundException e) {
 			logger.fatal(method_name + e.getMessage());
 		}
 		try {
 			propDB.load(fileStreamDB);
 		    logger.trace(method_name + propDB.toString());
 		} catch (IOException e) {
 			logger.fatal(method_name + e.getMessage());
 		}
  		this.stagingDBActorName = propDB.getProperty("stagingDBActorName");
  		this.persistenceDBActorName = propDB.getProperty("persistenceDBActorName");
  		this.stagingDBAskTimeOutSecs = Integer.parseInt(propDB.getProperty("stagingDBAskTimeOutSecs"));
  		this.stagingDBLockTimeOutSecs = Integer.parseInt(propDB.getProperty("stagingDBLockTimeOutSecs"));
  		this.persistenceDBAskTimeOutSecs = Integer.parseInt(propDB.getProperty("persistenceDBAskTimeOutSecs"));
  		this.persistenceDBLockTimeOutSecs = Integer.parseInt(propDB.getProperty("persistenceDBAskTimeOutSecs"));
  		this.stagingDBPostingTableName = propDB.getProperty("stagingDBPostingTableName");
  		this.stagingDBErrorTableName = propDB.getProperty("stagingDBErrorTableName");
  		this.persistenceDBServiziCollectionName = propDB.getProperty("persistenceDBServiziCollectionName");
  		
  		// Get PersistenceDB ActorRef
 		try {
 			FiniteDuration timeout = Duration.create(10, TimeUnit.SECONDS); 	 		
 	 		Future<ActorRef> persistenceDBActorRefFuture = this.getContext()
 	 															.getSystem()
 	 															.actorSelection("user/" + this.persistenceDBActorName)
 	 															.resolveOne(timeout);
			this.persistenceDBActorRef = (ActorRef) Await.result(persistenceDBActorRefFuture, timeout);
			logger.info("Obtainined PersistenceDB Actor Ref");
		} catch (Exception e) {
			logger.fatal("Failed to Obtain PersistenceDB Actor Ref: " + e.getMessage());
		}
  	 		
 		// Get StagingDB ActorRef
		try {
			FiniteDuration timeout = Duration.create(10, TimeUnit.SECONDS); 	 		
	 		Future<ActorRef> stagingDBActorRefFuture = this.getContext()
	 														.getSystem()
	 														.actorSelection("user/" + this.stagingDBActorName)
	 														.resolveOne(timeout);
			this.stagingDBActorRef = (ActorRef) Await.result(stagingDBActorRefFuture, timeout);
			logger.info("Obtainined StagingDB Actor Ref");
		} catch (Exception e) {
			logger.fatal("Failed to Obtain StagingDB Actor Ref: " + e.getMessage());
		}
        
        // Setting to NULL the cancellableJob
        this.cancellableJob = null;
        
 		// Creating TommyRestPoster Actor
        logger.info(method_name + "Creating TommyRestPost Actor ...");
        this.TommyRestPosterActorRef = this.getContext().getSystem().actorOf(Props.create(TommyRestPoster.class, confPath), "TommyRestPost");
        logger.info(method_name + "TommyRestPost Actor is Active");
        
        
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
        		.match(Start.class, this::onStart)
        		.match(PostOperationStart.class, this::onNewPostingOperation)
        		.match(PostStop.class, signal -> onPostStop())
        		.match(String.class, s -> {
					logger.info(this.getClass().getSimpleName() + " Received String message: {}", s);
	             })
				.matchAny(o -> logger.warn(this.getClass().getSimpleName() + " received unknown message"))
				.build();
    }

	@SuppressWarnings("deprecation")
	protected void onStart(Start command) {
    	
    	// Logger Method Name
		String method_name = "::onStart(): ";
		logger.info(method_name + "Received Start Event");
		
		// Tell TommyRestPoster to Start Posting When Asked
    	logger.info(method_name + "Tell TommyRestPoster to Start Posting When Asked");
    	this.TommyRestPosterActorRef.tell(new StartPosting(), this.getSelf());
    	
    	// Sleep 60 seconds
    	try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	// Create Scheduler for Periodic Operation
    	logger.info(method_name + "Scheduling TommyPostHandler to trigger a PostOperation every " + postOperationInterval + " seconds");
    	this.cancellableJob = this.getContext().getSystem().getScheduler().schedule(Duration.Zero(), 
    																				Duration.create(postOperationInterval, TimeUnit.SECONDS), 
    																				this.getSelf(), 
    																				new PostOperationStart(), 
    																				this.getContext().getSystem().dispatcher(), 
    																				this.getSelf());
    	
    	// Start Posting Operation
    	//this.onNewPostingOperation(new PostOperationStart());    	

    }
	
	protected void onNewPostingOperation(PostOperationStart newPostOperationStart) {
		
		// Method Name
		String method_name = "::onNewPostingOperation(): ";
		logger.trace(method_name + "Starting a new PostOperation");
		
		// Start New Posting Operation
		Boolean lockAcquiredStagingDB = false;
		Boolean lockAcquiredPersistenceDB = false;
		try {
		
			// Get the Lock for the Staging DB
			this.dbClient.acquireDBLockInfiniteLoop(this.getSelf(), actorID, this.stagingDBActorRef, 
													this.stagingDBAskTimeOutSecs, this.stagingDBLockTimeOutSecs);	
			lockAcquiredStagingDB = true;
		
	    	// Get the Lock for the Persistence DB
			this.dbClient.acquireDBLockInfiniteLoop(this.getSelf(), actorID, this.persistenceDBActorRef, 
													this.persistenceDBAskTimeOutSecs, this.persistenceDBLockTimeOutSecs);
			lockAcquiredPersistenceDB = true;
			
			// Instantiate the map of blocked automezzi
			TreeMap<String, Boolean> blockedMezziMap = new TreeMap<String, Boolean>();
		
			// Collect Every Servizio from StagingDB - Error Section
			logger.trace(method_name + "Reading all servizi from StagingDB - Error Section");
			ArrayList<TommyEnrichedJSON> serviziListError = this.dbClient.getAllServiziInCollection(this.getSelf(), 
																									actorID, 
																									this.stagingDBActorRef, 
																									this.stagingDBAskTimeOutSecs, 
																									this.stagingDBErrorTableName);
			logger.trace(method_name + "Read " + serviziListError.size() + " servizi from StagingDB - Error Section");
			
			// Block all Automezzi of servizi in error state from posting
			for (TommyEnrichedJSON servizioEnriched: serviziListError) {
				blockedMezziMap.put(servizioEnriched.getCodiceMezzo(), false);
				logger.error(method_name + "Mezzo " + servizioEnriched.getCodiceMezzo() + " was blocked from posting");
			}
			
			// Collect Every Servizio from StagingDB - Posting Section
			logger.trace(method_name + "Reading all servizi from StagingDB - Posting Section");
			ArrayList<TommyEnrichedJSON> serviziEnrichedListToPost = this.dbClient.getAllServiziInCollection(this.getSelf(), 
																										actorID, 
																										this.stagingDBActorRef, 
																										this.stagingDBAskTimeOutSecs, 
																										this.stagingDBPostingTableName);
			logger.trace(method_name + "Read " + serviziEnrichedListToPost.size() + " servizi from StagingDB - Posting Section");
			
			// Put all read servizi from posting section to a sorted tree map
			TreeMap<String, TommyEnrichedJSON> serviziToPost = new TreeMap<String, TommyEnrichedJSON>();
			for (TommyEnrichedJSON servizioEnriched: serviziEnrichedListToPost) {
				serviziToPost.put(servizioEnriched.getCodiceServizio(), servizioEnriched);
			}
			
			// Process all Servizi in serviziToPost to determine which ones to post
			TreeMap<String, TommyEnrichedJSON> serviziUnpostable = new TreeMap<String, TommyEnrichedJSON>();
			TreeMap<String, TreeMap<String, TommyEnrichedJSON>> serviziToPostByMezzo = new TreeMap<String, TreeMap<String, TommyEnrichedJSON>>();
			TreeMap<String, TommyEnrichedJSON> serviziPosted = new TreeMap<String, TommyEnrichedJSON>();
			for (String servizioID: serviziToPost.keySet()) {
				TommyEnrichedJSON servizioEnriched = serviziToPost.get(servizioID);					
				if (servizioID.compareTo(this.startingServizioID) < 0) { // Check against startingServizioID
					logger.warn(method_name + "Servizio " + servizioID + " was before startingServizioID " + startingServizioID);
					/**
					if (servizioInError(servizioID, servizioEnriched)) { // Servizio Must Go to Staging DB - Error Section
						serviziUnpostable.put(servizioID, servizioEnriched);
						logger.error(method_name + "Servizio " + servizioID + " will be put in the StagingDB - Error Section");
					} else { // Servizio is not In Error State
						if (validateServizioForPosting(servizioID, servizioEnriched, new TreeMap<String, Boolean>())) { // Servizio mustGo to PersistenceDB
							serviziPosted.put(servizioID, servizioEnriched);
							logger.warn(method_name + "Servizio " + servizioID + " will be put in the PersistenceDB");
						} else { // Servizio will Remain in Posting section, but the mezzo will be blocked from posting
							logger.warn(method_name + "Servizio " + servizioID + " is not valid for posting and will remain in StagingDB - Posting Sectio");
						}
					}
					*/
				} else { // Validate for Posting	
					if (servizioInError(servizioID, servizioEnriched)) { // Servizio Must Go to Staging DB - Error Section
						serviziUnpostable.put(servizioID, servizioEnriched);
						logger.error(method_name + "Servizio " + servizioID + " will be put in the StagingDB - Error Section");
					} else { // Servizio is not In Error State
						if (validateServizioForPosting(servizioID, servizioEnriched, blockedMezziMap)) { // Servizio can be posted
							if (!serviziToPostByMezzo.containsKey(servizioEnriched.getCodiceMezzo())) { // Map For Mezzo Does Not Exists
								serviziToPostByMezzo.put(servizioEnriched.getCodiceMezzo(), new TreeMap<String, TommyEnrichedJSON>());
							}
							serviziToPostByMezzo.get(servizioEnriched.getCodiceMezzo()).put(servizioID, servizioEnriched);
							logger.trace(method_name + "Servizio " + servizioID + " was validated for posting");
						} else { // Servizio will Remain in Posting section, but the mezzo will be blocked from posting
							blockedMezziMap.put(servizioEnriched.getCodiceMezzo(), false);
							logger.warn(method_name + "Mezzo " + servizioEnriched.getCodiceMezzo() + " was blocked from posting");
							logger.warn(method_name + "Servizio " + servizioID + " was blocked from posting since is not valid yet");
						}
					}
				}
			}
			
			// Post Servizi Grouped by Mezzo
			for (String automezzo: serviziToPostByMezzo.keySet() ) {
				logger.trace(method_name, "Posting Servizi for mezzo " + automezzo);
				PostData postData = new PostData(automezzo, serviziToPostByMezzo.get(automezzo));
				if (this.save_post_json_to_disk) {
					String fileName = new SimpleDateFormat("'postData_'yyyyMMdd_HHmmss'.json'").format(new Date());
					FileOutputStream outputStream = new FileOutputStream(this.post_json_folder + "/" + fileName);
			        outputStream.write(postData.getJsonServizi().getBytes());     
			        outputStream.close();
				}					
				Future<Object> postResponse = Patterns.ask(this.TommyRestPosterActorRef, postData, 10000);
				
				try {
					PostDataResponse response = (PostDataResponse) Await.result(postResponse, Duration.create(tommyPostTimeout, TimeUnit.SECONDS));
					if (response.getSuccess()) {
						logger.trace(method_name, "Post Success Servizi for Automezzo " + automezzo);
						logger.trace(method_name, response.getTommyResponse());
						serviziPosted.putAll(serviziToPostByMezzo.get(automezzo));
						logger.trace(method_name, "Added " + serviziToPostByMezzo.get(automezzo).size() + 
													" servizi for automezzo " + automezzo +
													" to serviziPosted Map");
						if (this.save_post_json_to_disk) {
							String fileName = new SimpleDateFormat("'postReplySuccess'yyyyMMdd_HHmmss'.json'").format(new Date());
							FileOutputStream outputStream = new FileOutputStream(this.post_json_folder + "/" + fileName);
					        outputStream.write(response.getTommyResponse().getBytes());     
					        outputStream.close();
						}	
					} else {
						logger.error(method_name, "Post Error Servizi for mezzo " + automezzo);
						logger.error(method_name, response.getTommyResponse());
						serviziUnpostable.putAll(serviziToPostByMezzo.get(automezzo));
						logger.warn(method_name, "Added " + serviziToPostByMezzo.get(automezzo).size() + 
								" servizi for automezzo " + automezzo +
								" to serviziUnpostable Map");
						if (this.save_post_json_to_disk) {
							String fileName = new SimpleDateFormat("'postReplyFaillure'yyyyMMdd_HHmmss'.json'").format(new Date());
							FileOutputStream outputStream = new FileOutputStream(this.post_json_folder + "/" + fileName);
					        outputStream.write(response.getTommyResponse().getBytes());     
					        outputStream.close();
						}
					}
				} catch (TimeoutException | InterruptedException | IllegalArgumentException e) {
					logger.error(method_name + " Failed to post servizi for Automezzo " + automezzo
											 + ": " + e.getMessage());
				}
			}
			
			// Write Posted Servizi to PersistenceDB and Remove Posted Servizi From StagingDB Posting Section
			if (this.writeOverPersistenceDB) {
				for (String servizioID: serviziPosted.keySet()) {
					TommyEnrichedJSON servizioEnriched = serviziPosted.get(servizioID);
					this.dbClient.writeNewServizioByID(this.getSelf(), actorID, this.persistenceDBActorRef, 
							 this.persistenceDBAskTimeOutSecs, 
							 servizioID, servizioEnriched.getJsonServizio(), 
							 this.persistenceDBServiziCollectionName);
					logger.trace(method_name + "Written servizio " + servizioID + " to PersistenceDB - " + this.persistenceDBServiziCollectionName);
					this.dbClient.removeServizioByID(this.getSelf(), actorID, this.stagingDBActorRef, 
							 this.persistenceDBAskTimeOutSecs, 
							 servizioID, 
							 this.stagingDBPostingTableName);
					logger.trace(method_name + "Removed servizio " + servizioID + " from StagingDB - " + this.stagingDBPostingTableName);
				}
			} else {
				logger.warn(method_name + "Write over PersistenceDB was disabled by configuration");
			}
			
			// Move Unpostable Servizi from StagingDB Posting Section to Error Section
			for (String servizioID: serviziUnpostable.keySet()) {
				this.dbClient.moveServizioByID(this.getSelf(), actorID, this.stagingDBActorRef, 
						 this.stagingDBAskTimeOutSecs, 
						 servizioID,
						 this.stagingDBPostingTableName, this.stagingDBErrorTableName);
				logger.trace(method_name + "Moved servizio " + servizioID + " in StagingDB from " + stagingDBPostingTableName + " to " + stagingDBErrorTableName);
			}
			
		} catch (Exception e) {
			logger.error(method_name + "Failed Post Operation: " + e.getMessage());	
		} finally {			
			try {
				if (lockAcquiredPersistenceDB)
					this.dbClient.releaseDBLock(this.getSelf(), actorID, this.persistenceDBActorRef, this.persistenceDBAskTimeOutSecs);
			} catch (DBOperationFailedException e) {
				logger.error(method_name + "Failed to release Persistence DB Lock: " + e.getMessage());
			}
			try {
				if (lockAcquiredStagingDB) 
					this.dbClient.releaseDBLock(this.getSelf(), actorID, this.stagingDBActorRef, this.stagingDBAskTimeOutSecs);
			} catch (DBOperationFailedException e) {
				logger.error(method_name + "Failed to release Staging DB Lock: " + e.getMessage());
			}
		}
		
		
	}
	
	protected boolean servizioInError(String servizioID, TommyEnrichedJSON servizioEnriched) {
		
    	// Method Name
    	String method_name = "::servizioInError(): ";
    	
    	// Check ServizioID
    	if (servizioID == null) {
    		logger.error(method_name, "Received servizio has null servizioID");
    		return true;
    	}
    	if (servizioID.isBlank()) {
    		logger.error(method_name, "Received servizio has blanck servizioID");
    		return true;
    	}
    	
    	// Check servizioEnriched
    	if (servizioEnriched == null) {
    		logger.error(method_name, "Received servizio was null");
    		return true;
    	}
    	
    	// Check Mezzo Servizio
    	if (servizioEnriched.getCodiceMezzo() == null) {
    		logger.error(method_name, "Servizio " + servizioID + " has null codiceMezzo");
    		return true;
    	}
    	if (servizioEnriched.getCodiceMezzo().isBlank()) {
    		logger.error(method_name, "Servizio " + servizioID + " has blanck codiceMezzo");
    		return true;
    	}
    			
		return false;
	}
    
    protected boolean validateServizioForPosting(String servizioID, TommyEnrichedJSON servizioEnriched, TreeMap<String, Boolean> blockedMezziMap) {
		
    	// Method Name
    	String method_name = "::validateServizioForPosting(): ";
    	
    	if (servizioInError(servizioID, servizioEnriched)) {
    		return false;
    	}
    	
    	// Check if KM Servizio are More than 0
    	if (servizioEnriched.getKm() <= 0) {
    		logger.error(method_name, "Servizio " + servizioID + " has non positive KM");
    		return false;
    	}
    	
    	// Check blockedMezziMap
    	if (blockedMezziMap == null) {
    		logger.error(method_name, "Received blockedMezziMap was null");
    		return false;
    	}
    	
    	
    	// Check blockedMezziMap
    	if (blockedMezziMap.containsKey(servizioEnriched.getCodiceMezzo())) {
    		logger.error(method_name, "Servizio " + servizioID + " has blocked AutoMezzo " + servizioEnriched.getCodiceMezzo());
    		return false;
    	}
    			
		return true;
	}

	protected void onPostStop() {
		
		// Logger Method Name
		String method_name = "::onPostStop(): ";
		logger.info(method_name + "Received Stop Event");
		
	}

    
    public static void main(String[] args) {
		
		// Logger
		String method_name = "::main(): ";
		String confPath = "conf/tommy_posthandler.conf";
		
		// Define and Load Configuration File
 		Properties prop = new Properties();
 		FileInputStream fileStream = null;
 		try {
 			fileStream = new FileInputStream(confPath);
 		} catch (FileNotFoundException e) {
 			
 		}
 		try {
 		    prop.load(fileStream); 		   
 		} catch (IOException e) {
 			
 		}
 		
		// Logger
 		String loggerPath = prop.getProperty("logger_conf");
		System.setProperty("log4j2.configurationFile", loggerPath);
		org.apache.logging.log4j.Logger logger = LogManager.getLogger("TommyPostHandler");
		
		// Create Actor System
		logger.info(method_name + "Creating ActorSystem ...");
		ActorSystem system = ActorSystem.create("TommyPosterHandler");
		logger.info(method_name + system.name() + " ActorSystem is Active");
		
		// Creating Tommy Post Handler Actor
        logger.info(method_name + "Creating Tommy Post Handler Actor ...");
        ActorRef tommyPostHandlerActorRef = system.actorOf(Props.create(TommyPostHandler.class, confPath), "TommyPostHandler");
        logger.info(method_name + "Tommy Post Handler Actor is Active");
		
		// Send Start to TommyPostHandler
		logger.info(method_name + "Sending TommyPostHandler Actor the Start Msg ...");
		tommyPostHandlerActorRef.tell(new TommyPostHandler.Start(tommyPostHandlerActorRef.getClass().getSimpleName()), ActorRef.noSender());
		logger.info(method_name + "Sent :)");
		
		
		/// Wait for user quit
		Scanner scan = new Scanner(System.in);
		System.out.print("Press any key to quit . . . ");
	    scan.nextLine();
	    scan.close();
			    
		
			
	}
    
	

}
