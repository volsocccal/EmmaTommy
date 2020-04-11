package emmaTommy.TommyDataHandler.Actors;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Cancellable;
import akka.actor.Props;
import akka.actor.typed.PostStop;
import emmaTommy.TommyDataHandler.ActorsMessages.StartConsuming;
import emmaTommy.TommyDataHandler.ActorsMessages.StartPosting;
import scala.concurrent.duration.Duration;
import java.util.concurrent.TimeUnit;

public class TommyOrchestrator extends AbstractActor {
	
	protected org.apache.logging.log4j.Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
	
	protected String tommy_orchestrator_conf;
	protected String tommy_mysql_conf;	
	protected String tommy_posthandler_conf;	
	protected String tommy_consumer_conf;	
	
	protected ActorRef TommyConsumerActorRef;
	protected ActorRef TommyMySqlHandlerActorRef;
	protected ActorRef TommyPostHandlerActorRef;

	public static class Start {
        public final String name;

        public Start(String name) {
            this.name = name;
        }
    }
	
	public static Props props(String text, String confPath) {
        return Props.create(TommyOrchestrator.class, text, confPath);
    }
	
	private TommyOrchestrator(String confPath) {
        
        // Logger Method Name
        String method_name = "::TommyOrchestrator(): ";
        
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
 		this.tommy_mysql_conf = prop.getProperty("tommy_mysql_conf");
 		this.tommy_orchestrator_conf = prop.getProperty("tommy_orchestrator_conf");
 		this.tommy_posthandler_conf = prop.getProperty("tommy_posthandler_conf");
 		this.tommy_consumer_conf = prop.getProperty("tommy_consumer_conf");
 		        
 		// Creating Tommy Post Handler Actor
        logger.info(method_name + "Creating Tommy Post Handler Actor ...");
        this.TommyPostHandlerActorRef = this.getContext().getSystem().actorOf(Props.create(TommyPostHandler.class, tommy_posthandler_conf), "TommyPostHandlerActor");
        logger.info(method_name + "Tommy Post Handler Actor is Active");
        
        // Creating Tommy Kafka Consumer Actor
        logger.info(method_name + "Creating Tommy Kafka Consumer Actor ...");
        this.TommyConsumerActorRef = this.getContext().getSystem().actorOf(Props.create(TommyKafkaConsumer.class, tommy_consumer_conf), "TommyKafkaConsumerActor");
        logger.info(method_name + "Tommy Kafka Consumer Actor is Active");
        
        
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
        		.match(Start.class, this::onStart)
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
		
		// Tell TommyMongoHandler to Connect to DB
		logger.info(method_name + "Tell TommyMySqlHandler to Start the DB Handling");
		//this.TommyMySqlHandlerActorRef.tell(new MongoDBConnect(this.missioniCollectionName, this.serviziCollectionName), this.getSelf());
		
		// Tell TommyPostHandlerActor to Start Posting When Asked
    	logger.info(method_name + "Tell TommyPostHandlerActor to Start Posting When Asked");
    	this.TommyPostHandlerActorRef.tell(new StartPosting(), this.getSelf());
    	
    	// Tell EmmaTommyConverterActor to Start
    	logger.info(method_name + "Tell TommyConsumerActor to Start Consuming");
    	this.TommyConsumerActorRef.tell(new StartConsuming(this.TommyMySqlHandlerActorRef, true), 
    									this.getSelf());
    	
    }
    
    protected void onPostStop() {
		
		// Logger Method Name
		String method_name = "::onPostStop(): ";
		logger.info(method_name + "Received Stop Event");
		
	}

	public static void main(String[] args) {
		
		// Logger
		String method_name = "::main(): ";
		org.apache.logging.log4j.Logger logger = LogManager.getLogger("TommyOrchestrator");
		
		// Create Actor System
		logger.info(method_name + "Creating ActorSystem ...");
		ActorSystem system = ActorSystem.create("test-system");
		logger.info(method_name + system.name() + " ActorSystem is Active");
		
		// Create EmmaOrchestrator Actor
		logger.info(method_name + "Creating TommyOrchestrator Actor ...");
		ActorRef orchestrator = system.actorOf(Props.create(TommyOrchestrator.class, "conf/tommy_orchestrator.conf"), "TommyOrchestrator");
		logger.info(method_name + " TommyOrchestrator Actor is Active");
		
		// Send Start to EmmaOrchestrator
		logger.info(method_name + "Sending TommyOrchestrator Actor the Start Msg ...");
		orchestrator.tell(new TommyOrchestrator.Start(orchestrator.getClass().getSimpleName()), ActorRef.noSender());
		logger.info(method_name + "Sent :)");
		
		
		/// Wait for user quit
		Scanner scan = new Scanner(System.in);
		System.out.print("Press any key to quit . . . ");
	    scan.nextLine();
	    scan.close();

	}

}
