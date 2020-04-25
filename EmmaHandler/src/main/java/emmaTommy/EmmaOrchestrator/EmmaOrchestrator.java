package emmaTommy.EmmaOrchestrator;

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
import scala.concurrent.duration.Duration;
import java.util.concurrent.TimeUnit;
import emmaTommy.EmmaParser.Actors.EmmaJSONProducer;
import emmaTommy.EmmaParser.Actors.EmmaXMLParserActor;
import emmaTommy.EmmaREST.Actors.EmmaRESTActor;
import emmaTommy.EmmaREST.ActorsMessages.StartREST;

public class EmmaOrchestrator extends AbstractActor {
	
	protected org.apache.logging.log4j.Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
	
	protected String emma_rest_conf;
	protected String emma_psswd_conf;
	protected String emma_orchestrator_conf;
	protected String emma_parser_conf;
	protected String emma_producer_conf;	
	int requestTimeSecs;
	
	protected ActorRef emmaProducerActorRef;
	protected ActorRef emmaParserActorRef;
	protected ActorRef emmaRestActorRef;
	protected Cancellable cancellableJob;
	
	public static class Start {
        public final String name;

        public Start(String name) {
            this.name = name;
        }
    }
	
	public static Props props(String text, String confPath) {
        return Props.create(EmmaOrchestrator.class, text, confPath);
    }
	
	private EmmaOrchestrator(String confPath) {
        
        // Logger Method Name
        String method_name = "::EmmaOrchestrator(): ";
        
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
 		this.emma_rest_conf = prop.getProperty("emma_rest_conf");
 		this.emma_psswd_conf = prop.getProperty("emma_psswd_conf");
 		this.emma_orchestrator_conf = prop.getProperty("emma_orchestrator_conf");
 		this.emma_parser_conf = prop.getProperty("emma_parser_conf");
 		this.emma_producer_conf = prop.getProperty("emma_producer_conf");
		this.requestTimeSecs = Integer.parseInt(prop.getProperty("requestTimeSecs"));
        
        // Setting to NULL the cancellableJob
        this.cancellableJob = null;
        
        // Creating Emma JSON Producer Actor
     	logger.info(method_name + "Creating Emma JSON Producer Actor ...");
     	this.emmaProducerActorRef = this.getContext().getSystem().actorOf(Props.create(EmmaJSONProducer.class, emma_producer_conf), "EmmaJSONProducer");
        logger.info(method_name + "Emma JSON Producer Actor is Active");
        
        // Creating Emma XML Parser Actor
        logger.info(method_name + "Creating Emma XML Parser Actor ...");
        this.emmaParserActorRef = this.getContext().getSystem().actorOf(Props.create(EmmaXMLParserActor.class, emma_parser_conf), "EmmaXMLParserActor");
        logger.info(method_name + "Emma XML Parser Actor is Active");
        
        // Creating Emma Rest Actor
        logger.info(method_name + "Creating Emma REST Actor ...");
        this.emmaRestActorRef = this.getContext().getSystem().actorOf(Props.create(EmmaRESTActor.class, emma_rest_conf, emma_psswd_conf), "EmmaRESTActor");
        logger.info(method_name + "Emma REST Actor is Active");
        
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
    	
		// Build Start REST msg
    	StartREST startREST = new StartREST(this.emmaParserActorRef, this.emmaProducerActorRef);
    	logger.info(method_name + "Built Start REST msg");
    	
    	// Tell EmmaRestActor to Start
    	logger.info(method_name + "Tell EmmaRestActor to Start");
    	this.emmaRestActorRef.tell(startREST, this.getSelf());
    	
    	// 
    	logger.info(method_name + "Scheduling EmmaRestActor Download every " + requestTimeSecs + " seconds");
    	this.cancellableJob = this.getContext().getSystem().getScheduler().schedule(Duration.Zero(), 
    																				Duration.create(requestTimeSecs, TimeUnit.SECONDS), 
    																				this.emmaRestActorRef, 
    																				startREST, 
    																				this.getContext().getSystem().dispatcher(), 
    																				this.getSelf());
    }
    
    protected void onPostStop() {
		
		// Logger Method Name
		String method_name = "::onPostStop(): ";
		logger.info(method_name + "Received Stop Event");
		
		// Stopping Scheduled REST Download Job
		if (this.cancellableJob != null) {
			logger.info(method_name + "Stopping Scheduled REST Download Job");
			this.cancellableJob.cancel();
			this.cancellableJob = null;
			logger.info(method_name + "Stopped Scheduled REST Download Job");
		}
		
	}

	public static void main(String[] args) {
		
		String method_name = "::main(): ";
		String confPath = "conf/emma_orchestrator.conf";
				
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
		org.apache.logging.log4j.Logger logger = LogManager.getLogger("EmmaOrchestrator");
		
		// Create Actor System
		logger.info(method_name + "Creating ActorSystem ...");
		ActorSystem system = ActorSystem.create("EmmaHandler");
		logger.info(method_name + system.name() + " ActorSystem is Active");
		
		// Create EmmaOrchestrator Actor
		logger.info(method_name + "Creating EmmaOrchestrator Actor ...");
		ActorRef orchestrator = system.actorOf(Props.create(EmmaOrchestrator.class, confPath), "EmmaOrchestrator");
		logger.info(method_name + " EmmaOrchestrator Actor is Active");
		
		// Send Start to EmmaOrchestrator
		logger.info(method_name + "Sending EmmaOrchestrator Actor the Start Msg ...");
		orchestrator.tell(new EmmaOrchestrator.Start(orchestrator.getClass().getSimpleName()), ActorRef.noSender());
		logger.info(method_name + "Sent :)");
		
		
		/// Wait for user quit
		Scanner scan = new Scanner(System.in);
		System.out.print("Press any key to quit . . . ");
	    scan.nextLine();
	    scan.close();

	}

}
