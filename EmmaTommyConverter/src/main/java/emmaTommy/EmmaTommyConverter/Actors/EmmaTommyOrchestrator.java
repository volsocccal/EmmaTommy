package emmaTommy.EmmaTommyConverter.Actors;

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
import emmaTommy.EmmaTommyDataConverter.ActorsMessages.StartConsuming;
import emmaTommy.EmmaTommyDataConverter.ActorsMessages.StartConversion;
import emmaTommy.EmmaTommyDataConverter.ActorsMessages.StartProducing;

public class EmmaTommyOrchestrator extends AbstractActor {
	
	protected org.apache.logging.log4j.Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
	
	protected String emmatommy_conf;
	protected String emmatommy_orchestrator_conf;
	protected String emmatommy_converter_conf;	
	protected String emmatommy_producer_conf;	
	protected String emmatommy_consumer_conf;	
	
	protected ActorRef emmaTommyConsumerActorRef;
	protected ActorRef emmaTommyProducerActorRef;
	protected ActorRef emmaTommyConverterActorRef;
	
	
	public static class Start {
        public final String name;

        public Start(String name) {
            this.name = name;
        }
    }
	
	public static Props props(String text, String confPath) {
        return Props.create(EmmaTommyOrchestrator.class, text, confPath);
    }
	
	private EmmaTommyOrchestrator(String confPath) {
        
        // Logger Method Name
        String method_name = "::EmmaTommyOrchestrator(): ";
        
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
 		this.emmatommy_converter_conf = prop.getProperty("emmatommy_converter_conf");
 		this.emmatommy_orchestrator_conf = prop.getProperty("emmatommy_orchestrator_conf");
 		this.emmatommy_producer_conf = prop.getProperty("emmatommy_producer_conf");
 		this.emmatommy_consumer_conf = prop.getProperty("emmatommy_consumer_conf");
 		

        // Creating EmmaTommy Kafka Producer Actor
        logger.info(method_name + "Creating EmmaTommy Kafka Producer Actor ...");
        this.emmaTommyProducerActorRef = this.getContext().getSystem().actorOf(Props.create(EmmaTommyKafkaProducer.class, emmatommy_producer_conf), "EmmaTommyKafkaProducerActor");
        logger.info(method_name + "Emma Tommy Kafka Producer Actor is Active");
         
        // Creating EmmaTommy Converter Actor
        logger.info(method_name + "Creating EmmaTommy Converter Actor ...");
        this.emmaTommyConverterActorRef = this.getContext().getSystem().actorOf(Props.create(EmmaTommyJsonConverter.class, emmatommy_converter_conf), "EmmaTommyConverterActor");
        logger.info(method_name + "EmmaTommy Converter Actor is Active");
        
        // Creating EmmaTommy Kafka Consumer Actor
        logger.info(method_name + "Creating EmmaTommy Kafka Consumer Actor ...");
        this.emmaTommyConsumerActorRef = this.getContext().getSystem().actorOf(Props.create(EmmaTommyKafkaConsumer.class, emmatommy_consumer_conf), "EmmaTommyKafkaConsumerActor");
        logger.info(method_name + "Emma Tommy Kafka Consumer Actor is Active");
        
        
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
		
		// Tell emmaTommyProducerActorRef to Start
    	logger.info(method_name + "Tell EmmaTommyProducerActor to Start Producing");
    	this.emmaTommyProducerActorRef.tell(new StartProducing(), this.getSelf());
    	
    	// Tell EmmaTommyDataConverterActor to Start Converting
    	logger.info(method_name + "Tell EmmaTommyDataConverterActor to Start Converting");
    	this.emmaTommyConverterActorRef.tell(new StartConversion(this.emmaTommyProducerActorRef, true), this.getSelf());
    	//this.emmaTommyConverterActorRef.tell(new StartConversion(null, false, null, false, this.serviziCollectionName), this.getSelf());
    	
    	// Tell EmmaTommyConsumerActor to Start
    	logger.info(method_name + "Tell EmmaTommyConsumerActor to Start Consuming");
    	this.emmaTommyConsumerActorRef.tell(new StartConsuming(this.emmaTommyConverterActorRef), this.getSelf());
    	
    }
    
    protected void onPostStop() {
		
		// Logger Method Name
		String method_name = "::onPostStop(): ";
		logger.info(method_name + "Received Stop Event");
		
	}

	public static void main(String[] args) {
		
		String method_name = "::main(): ";
		String confPath = "conf/emmatommy_orchestrator.conf";
				
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
		org.apache.logging.log4j.Logger logger = LogManager.getLogger("EmmaTommyOrchestrator");
		
		// Create Actor System
		logger.info(method_name + "Creating ActorSystem ...");
		ActorSystem system = ActorSystem.create("EmmaTommyConverter");
		logger.info(method_name + system.name() + " ActorSystem is Active");
		
		// Create EmmaOrchestrator Actor
		logger.info(method_name + "Creating EmmaTommyOrchestrator Actor ...");
		ActorRef orchestrator = system.actorOf(Props.create(EmmaTommyOrchestrator.class, confPath), "EmmaTommyOrchestrator");
		logger.info(method_name + " EmmaTommyOrchestrator Actor is Active");
		
		// Send Start to EmmaOrchestrator
		logger.info(method_name + "Sending EmmaTommyOrchestrator Actor the Start Msg ...");
		orchestrator.tell(new EmmaTommyOrchestrator.Start(orchestrator.getClass().getSimpleName()), ActorRef.noSender());
		logger.info(method_name + "Sent :)");
		
		
		/// Wait for user quit
		Scanner scan = new Scanner(System.in);
		System.out.print("Press any key to quit . . . ");
	    scan.nextLine();
	    scan.close();

	}

}
