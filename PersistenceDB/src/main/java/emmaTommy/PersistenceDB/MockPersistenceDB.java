package emmaTommy.PersistenceDB;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import emmaTommy.DBAbstraction.DBHandlers.MockDB;
public class MockPersistenceDB {

	public MockPersistenceDB() {
		// TODO Auto-generated constructor stub
	}
	
public static void main(String[] args) {
		
		String method_name = "::main(): ";
		String confPath = "conf/db_persistence.conf";
				
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
 		
 		// Read Properties
 		String loggerPath = prop.getProperty("logger_conf");
 		Boolean useMock = (Integer.parseInt(prop.getProperty("useMock")) == 0) ? (true) : (false);
 		String loggerName = "";
 		if (useMock)
 			loggerName = prop.getProperty("mockLogger");
 		else
 			loggerName = prop.getProperty("realLogger");
 		
		// Logger
		System.setProperty("log4j2.configurationFile", loggerPath);
		org.apache.logging.log4j.Logger logger = LogManager.getLogger(loggerName);
		
		// Build Akka System Config Map
		HashMap<String, String> systemConfigMap = new HashMap<String, String>();
		systemConfigMap.put("akka.actor.serializers.jackson-json", "akka.serialization.jackson.JacksonJsonSerializer");
		systemConfigMap.put("akka.actor.provider", "cluster");
		systemConfigMap.put("akka.remote.artery.transport", "tcp");
		systemConfigMap.put("akka.remote.transport.canonical.hostname", "127.0.0.1");
		systemConfigMap.put("akka.remote.transport.canonical.port", "5150");
		/**
		 * systemConfigMap.put("akka.actor.provider", "akka.remote.RemoteActorRefProvider");
		systemConfigMap.put("akka.logLevel", "INFO");
		systemConfigMap.put("akka.remote.transports", "akka.remote.netty.tcp");
		systemConfigMap.put("akka.remote.netty.tcp.hostname", "127.0.0.1");
		systemConfigMap.put("akka.remote.netty.tcp.port", "5150");
		systemConfigMap.put("akka.remote.log-sent-messages", "on");
		systemConfigMap.put("akka.remote.log-received-messages", "on");
		 */
		
		// Create System Config Object
	    Config systemConfig = ConfigFactory.parseMap(systemConfigMap);

	    // MockDB Data
		String DBName = loggerName;
		String DBTech = "MongoDB";
		String DBType = "NoSql";
		Boolean supportEnrichedJSON = true;
		ArrayList<String> collectionListNames = new ArrayList<String>() { 
			private static final long serialVersionUID = 1L;
			{ 
				add("soreuAlpina");
	            add("soreuLaghi"); 
	            add("soreuMetropolitana"); 
	            add("soreuPianura");
	        } 
	    }; 
	    
	    // Create Actor System
 		logger.info(method_name + "Creating ActorSystem ...");
 		ActorSystem system = ActorSystem.create(DBName, systemConfig);		
 		logger.info(method_name + system.name() + " ActorSystem is Active");
		
		// Create PersistenceDBHandler Actor
		logger.info(method_name + "Creating " + DBName + " Actor ...");
		ActorRef persistenceDBHandler = system.actorOf(Props.create(MockDB.class, 
																	DBName, 
																	DBTech, 
																	DBType, 
																	supportEnrichedJSON, 
																	collectionListNames), 
														DBName);
		logger.info(method_name + " " + DBName + " Actor is Active");
		
		
		
		/// Wait for user quit
		Scanner scan = new Scanner(System.in);
		System.out.print("Press any key to quit . . . ");
	    scan.nextLine();
	    scan.close();

	}


}
