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
import akka.actor.Props;
import akka.actor.typed.PostStop;
import emmaTommy.TommyDataHandler.ActorsMessages.StartConsuming;
import emmaTommy.TommyDataHandler.ActorsMessages.StopConsuming;
import emmaTommy.TommyDataHandler.ActorsMessages.StartDataWriting;
import emmaTommy.TommyDataHandler.ActorsMessages.StopDataWriting;

public class TommyDataHandlerOrchestrator extends AbstractActor {
	
	protected org.apache.logging.log4j.Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
	
	protected String tommydatahandler_orchestrator_conf;
	protected String tommydatahandler_consumer_conf;
	protected String tommydatahandler_datawriter_conf;
	
	protected ActorRef DataConsumerActorRef;
	protected ActorRef DataWriterActorRef;
	
	protected Boolean sendToDataWriter;

	public static class Start {
        public final String name;

        public Start(String name) {
            this.name = name;
        }
    }
	
	public static Props props(String text, String confPath) {
        return Props.create(TommyDataHandlerOrchestrator.class, text, confPath);
    }
	
	private TommyDataHandlerOrchestrator(String confPath) {
        
        // Logger Method Name
        String method_name = "::TommyDataHandlerOrchestrator(): ";
        
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
 		this.tommydatahandler_orchestrator_conf = prop.getProperty("tommydatahandler_orchestrator_conf");
 		this.tommydatahandler_consumer_conf = prop.getProperty("tommydatahandler_consumer_conf");
 		this.tommydatahandler_datawriter_conf = prop.getProperty("tommydatahandler_datawriter_conf");
 		this.sendToDataWriter = (Integer.parseInt(prop.getProperty("sendToDataWriter")) == 1) ? (true) : (false);
 		        
 		// Creating Data Writer Actor
        logger.info(method_name + "Creating TommyDataHandler DataWriter Actor ...");
        this.DataWriterActorRef = this.getContext().getSystem().actorOf(Props.create(TommyDataHandlerDataWriter.class, tommydatahandler_datawriter_conf), "TommyDataHandlerDataWriter");
        logger.info(method_name + "TommyDataHandler DataWriter is Active");
        
        // Creating Kafka Consumer Actor
        logger.info(method_name + "Creating TommyDataHandler Kafka Consumer Actor ...");
        this.DataConsumerActorRef = this.getContext().getSystem().actorOf(Props.create(TommyDataHandlerKafkaConsumer.class, tommydatahandler_consumer_conf), "TommyDataHandlerKafkaConsumer");
        logger.info(method_name + "TommyDataHandler Kafka Consumer Actor is Active");
        
        
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

	protected void onStart(Start command) {
    	
    	// Logger Method Name
		String method_name = "::onStart(): ";
		logger.info(method_name + "Received Start Event");
		
		// Tell TommyPostHandlerActor to Start Posting When Asked
    	logger.info(method_name + "Tell  " + DataWriterActorRef.path().name() + " to Start Writing Data When Asked");
    	this.DataWriterActorRef.tell(new StartDataWriting(), this.getSelf());
    	
    	// Tell EmmaTommyConverterActor to Start
    	logger.info(method_name + "Tell " + DataConsumerActorRef.path().name() + " to Start Consuming");
    	this.DataConsumerActorRef.tell(new StartConsuming(this.DataWriterActorRef, this.sendToDataWriter), 
    								   this.getSelf());
    	
    }
    
    protected void onPostStop() {
		
		// Logger Method Name
		String method_name = "::onPostStop(): ";
		logger.info(method_name + "Received Stop Event");
		
		// Tell TommyPostHandlerActor to Stop Writing Data
    	logger.info(method_name + "Tell  " + DataWriterActorRef.path().name() + " to Stop Writing Data When Asked");
    	this.DataWriterActorRef.tell(new StopDataWriting(), this.getSelf());
    	
    	// Tell EmmaTommyConverterActor to Start
    	logger.info(method_name + "Tell " + DataConsumerActorRef.path().name() + " to Stop Consuming");
    	this.DataConsumerActorRef.tell(new StopConsuming(), this.getSelf());
		
	}

	public static void main(String[] args) {
		
		String method_name = "::main(): ";
		String confPath = "conf/tommydatahandler_orchestrator.conf";
				
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
		org.apache.logging.log4j.Logger logger = LogManager.getLogger("TommyDataHandlerOrchestrator");
		
		// Create Actor System
		logger.info(method_name + "Creating ActorSystem ...");
		ActorSystem system = ActorSystem.create("TommyDataHandler");
		logger.info(method_name + system.name() + " ActorSystem is Active");
		
		// Create EmmaOrchestrator Actor
		logger.info(method_name + "Creating TommyDataHandlerOrchestrator Actor ...");
		ActorRef orchestrator = system.actorOf(Props.create(TommyDataHandlerOrchestrator.class, confPath), "TommyDataHandlerOrchestrator");
		logger.info(method_name + orchestrator.path().name() + " Actor is Active");
		
		// Send Start to EmmaOrchestrator
		logger.info(method_name + "Sending "  + orchestrator.path().name() + " the Start Msg ...");
		orchestrator.tell(new TommyDataHandlerOrchestrator.Start(orchestrator.getClass().getSimpleName()), ActorRef.noSender());
		logger.info(method_name + "Sent :)");
		
		
		/// Wait for user quit
		Scanner scan = new Scanner(System.in);
		System.out.print("Press any key to quit . . . ");
	    scan.nextLine();
	    scan.close();

	}

}
