 package emmaTommy.EmmaTommySupervisor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import emmaTommy.DBServerAbstraction.Actors.DBServer;
import emmaTommy.DBServerAbstraction.DBExceptions.UnknownDBException;
import emmaTommy.DBServerAbstraction.DBHandlers.MockDB;
import emmaTommy.DBServerAbstraction.DBHandlers.MongoDB;
import emmaTommy.DBServerAbstraction.DBHandlers.SqlLiteDB;
import emmaTommy.EmmaOrchestrator.EmmaOrchestrator;
import emmaTommy.EmmaTommyConverter.Actors.EmmaTommyOrchestrator;
import emmaTommy.TommyDataHandler.Actors.TommyDataHandlerOrchestrator;
import emmaTommy.TommyPoster.Actors.TommyPostHandler;

public class EmmaTommySupervisor {
	 
	 public static void main(String[] args) throws UnknownDBException {
			
			String method_name = "::main(): ";
			String supervisorConfPath = "conf/supervisor.conf";
					
			// Define and Load Configuration File
	 		Properties prop = new Properties();
	 		FileInputStream fileStream = null;
	 		try {
	 			fileStream = new FileInputStream(supervisorConfPath);
	 		} catch (FileNotFoundException e) {
	 			org.apache.logging.log4j.Logger logger = LogManager.getRootLogger();
	 			logger.fatal(method_name + "Couldn't load configuration file " + supervisorConfPath);
	 			return;
	 		}
	 		try {
	 		    prop.load(fileStream); 		   
	 		} catch (IOException e) {
	 			org.apache.logging.log4j.Logger logger = LogManager.getRootLogger();
	 			logger.fatal(method_name + "Couldn't load configuration file " + supervisorConfPath);
	 			return;
	 		}
	 		
			// Logger
	 		String loggerPath = prop.getProperty("logger_conf");
			System.setProperty("log4j2.configurationFile", loggerPath);
			org.apache.logging.log4j.Logger logger = LogManager.getLogger("EmmaTommySupervisor");
			
			// Create Actor System
			logger.info(method_name + "Creating ActorSystem ...");
			ActorSystem system = ActorSystem.create("EmmaTommySupervisor");
			logger.info(method_name + system.name() + " ActorSystem is Active");
			
			// Create StagingDB Actor
			String stagingDBConfPath = prop.getProperty("staging_db_conf");
	 		Properties stagingDBProp = new Properties();
	 		FileInputStream stagingDBConfFileStream = null;
	 		try {
	 			stagingDBConfFileStream = new FileInputStream(stagingDBConfPath);
	 		} catch (FileNotFoundException e) {
	 			logger.fatal(method_name + "Failed to read confFile: " + stagingDBConfPath);
	 			return;
	 		}
	 		try {
	 			stagingDBProp.load(stagingDBConfFileStream); 		   
	 		} catch (IOException e) {
	 			logger.fatal(method_name + "Failed to load confFile: " + stagingDBConfPath);
	 			return;
	 		}
	 		ActorRef stagingDBHandler = null;
	 		Boolean stagingDBUseMock = (Integer.parseInt(stagingDBProp.getProperty("useMock")) == 0) ? (true) : (false);
			String stagingDBName = stagingDBProp.getProperty("realLogger");
			String stagingDBInstanceName = stagingDBProp.getProperty("DBInstanceName");
			ArrayList<String> stagingCollectionListNames = new ArrayList<String>() { 
				private static final long serialVersionUID = 1L;
				{ 
					add("postingTable");
		        } 
		    }; 
			logger.info(method_name + "Creating " + stagingDBName + " Actor ...");
			if (stagingDBUseMock) {
				String stagingDBTech = stagingDBProp.getProperty("DBTech");
				String stagingDBType = stagingDBProp.getProperty("DBType");
				Boolean stagingDBSupportEnrichedJSON = (Integer.parseInt(stagingDBProp.getProperty("DBSupportEnrichedJSON")) == 1) ? (true) : (false);
				stagingDBHandler = system.actorOf(Props.create(DBServer.class, 
																stagingDBName, 
																new MockDB (stagingDBInstanceName, 
																			stagingDBTech, 
																			stagingDBType, 
																			stagingDBSupportEnrichedJSON, 
																			stagingCollectionListNames)), 
																stagingDBName);
			} else {
				String stagingDBInstanceConfPath = stagingDBProp.getProperty("instance_db_conf");
		 		Properties stagingDBInstanceProp = new Properties();
		 		FileInputStream stagingDBInstanceConfFileStream = null;
				try {
					stagingDBInstanceConfFileStream = new FileInputStream(stagingDBInstanceConfPath);
		 		} catch (FileNotFoundException e) {
		 			logger.fatal(method_name + "Failed to read confFile: " + stagingDBInstanceConfPath);
		 			return;
		 		}
		 		try {
		 			stagingDBInstanceProp.load(stagingDBInstanceConfFileStream); 		   
		 		} catch (IOException e) {
		 			logger.fatal(method_name + "Failed to load confFile: " + stagingDBInstanceConfPath);
		 			return;
		 		}
				String DBPath = stagingDBInstanceProp.getProperty("dbPath");
				stagingDBHandler = system.actorOf(Props.create(DBServer.class,
																stagingDBName, 
																new SqlLiteDB(stagingDBInstanceName, DBPath, stagingCollectionListNames.get(0))), 
																stagingDBName);
			}
			if (stagingDBHandler == null)
			{
				logger.fatal("Failed to create Staging DB Actor");
				return;
			}
			logger.info(method_name + " " + stagingDBName + " Actor is Active");
			
			
			// Create PersistenceDB Actor
			String persistenceDBConfPath = prop.getProperty("persistence_db_conf");
	 		Properties persistenceDBProp = new Properties();
	 		FileInputStream persistenceDBConfFileStream = null;
	 		try {
	 			persistenceDBConfFileStream = new FileInputStream(persistenceDBConfPath);
	 		} catch (FileNotFoundException e) {
	 			logger.fatal(method_name + "Failed to read confFile: " + persistenceDBConfPath);
	 			return;
	 		}
	 		try {
	 			persistenceDBProp.load(persistenceDBConfFileStream); 		   
	 		} catch (IOException e) {
	 			logger.fatal(method_name + "Failed to load confFile: " + persistenceDBConfPath);
	 			return;
	 		}
	 		
	 		ActorRef persistenceDBHandler = null;
	 		Boolean persistenceDBUseMock = (Integer.parseInt(persistenceDBProp.getProperty("useMock")) == 1) ? (true) : (false);
			String persistenceDBName = persistenceDBProp.getProperty("realLogger");
			String persistenceDBInstanceName = persistenceDBProp.getProperty("DBInstanceName");
			ArrayList<String> persistenceDBCollectionListNames = new ArrayList<String>() { 
				private static final long serialVersionUID = 1L;
				{ 
					add("soreuAlpina");
		            add("soreuLaghi"); 
		            add("soreuMetropolitana"); 
		            add("soreuPianura");
		        } 
		    }; 
			logger.info(method_name + "Creating " + persistenceDBName + " Actor ...");
			if (persistenceDBUseMock) {
				String persistenceDBTech = persistenceDBProp.getProperty("DBTech");
				String persistenceDBType = persistenceDBProp.getProperty("DBType");
				Boolean persistenceDBSupportEnrichedJSON = (Integer.parseInt(persistenceDBProp.getProperty("DBSupportEnrichedJSON")) == 0) ? (true) : (false);
				persistenceDBHandler = system.actorOf(Props.create(DBServer.class, 
																persistenceDBName, 
																new MockDB (persistenceDBInstanceName, 
																		persistenceDBTech, 
																		persistenceDBType, 
																		persistenceDBSupportEnrichedJSON, 
																		persistenceDBCollectionListNames)), 
																persistenceDBName);
			}
			else {
				String persistenceDBInstanceConfPath = persistenceDBProp.getProperty("instance_db_conf");
		 		Properties persistenceDBInstanceProp = new Properties();
		 		FileInputStream persistenceDBInstanceConfFileStream = null;
				try {
					persistenceDBInstanceConfFileStream = new FileInputStream(persistenceDBInstanceConfPath);
		 		} catch (FileNotFoundException e) {
		 			logger.fatal(method_name + "Failed to read confFile: " + persistenceDBInstanceConfPath);
		 			return;
		 		}
		 		try {
		 			persistenceDBInstanceProp.load(persistenceDBInstanceConfFileStream); 		   
		 		} catch (IOException e) {
		 			logger.fatal(method_name + "Failed to load confFile: " + persistenceDBInstanceConfPath);
		 			return;
		 		}
				String dbIp = persistenceDBInstanceProp.getProperty("dbIp");
				int dbPort = Integer.valueOf(persistenceDBInstanceProp.getProperty("dbPort"));
				String username = persistenceDBInstanceProp.getProperty("username");
				String psswd = persistenceDBInstanceProp.getProperty("psswd");
				persistenceDBHandler = system.actorOf(Props.create(DBServer.class,
																persistenceDBName,
																new MongoDB(persistenceDBName, dbIp, dbPort, username, psswd)), 
																persistenceDBName);
			}
			if (persistenceDBHandler == null)
			{
				logger.fatal("Failed to create Persistence DB Actor");
				return;
			}
			logger.info(method_name + " " + persistenceDBName + " Actor is Active");
			
			// Creating TommyPostHandler Actor
			String tommyPostHandlerOrchestratorConfPath = prop.getProperty("tommy_posthandler_conf");
	        logger.info(method_name + "Creating Tommy Post Handler Actor ...");
	        ActorRef tommyPostHandlerActorRef = system.actorOf(Props.create(TommyPostHandler.class, tommyPostHandlerOrchestratorConfPath), "TommyPostHandler");
	        logger.info(method_name + "Tommy Post Handler Actor is Active");
			logger.info(method_name + "Sending TommyPostHandler Actor the Start Msg ...");
			tommyPostHandlerActorRef.tell(new TommyPostHandler.Start(tommyPostHandlerActorRef.getClass().getSimpleName()), ActorRef.noSender());
			logger.info(method_name + "Sent :)");
			
			
			// Create TommyDataHandlerOrchestrator Actor
			String tommyDataHandlerOrchestratorConfPath = prop.getProperty("tommydatahandler_orchestrator_conf");
			logger.info(method_name + "Creating TommyDataHandlerOrchestrator Actor ...");
			ActorRef tommyDataHandlerOrchestrator = system.actorOf(Props.create(TommyDataHandlerOrchestrator.class, tommyDataHandlerOrchestratorConfPath), "TommyDataHandlerOrchestrator");
			logger.info(method_name + tommyDataHandlerOrchestrator.path().name() + " Actor is Active");
			logger.info(method_name + "Sending "  + tommyDataHandlerOrchestrator.path().name() + " the Start Msg ...");
			tommyDataHandlerOrchestrator.tell(new TommyDataHandlerOrchestrator.Start(tommyDataHandlerOrchestrator.getClass().getSimpleName()), ActorRef.noSender());
			logger.info(method_name + "Sent :)");
			
		
			
			// Create EmmaTommyOrchestrator Actor
			String emmaTommyOrchestratorConfPath = prop.getProperty("emmatommy_orchestrator_conf");
			logger.info(method_name + "Creating EmmaTommyOrchestrator Actor ...");
			ActorRef emmaTommyOrchestrator = system.actorOf(Props.create(EmmaTommyOrchestrator.class, emmaTommyOrchestratorConfPath), "EmmaTommyOrchestrator");
			logger.info(method_name + " EmmaTommyOrchestrator Actor is Active");
			logger.info(method_name + "Sending EmmaTommyOrchestrator Actor the Start Msg ...");
			emmaTommyOrchestrator.tell(new EmmaTommyOrchestrator.Start(emmaTommyOrchestrator.getClass().getSimpleName()), ActorRef.noSender());
			logger.info(method_name + "Sent :)");
			
			
			// Create EmmaOrchestrator Actor
			String emmaOrchestratorConfPath = prop.getProperty("emma_orchestrator_conf");
			logger.info(method_name + "Creating EmmaOrchestrator Actor ...");
			ActorRef emmaOrchestrator = system.actorOf(Props.create(EmmaOrchestrator.class, emmaOrchestratorConfPath), "EmmaOrchestrator");
			logger.info(method_name + " EmmaOrchestrator Actor is Active");
			logger.info(method_name + "Sending EmmaOrchestrator Actor the Start Msg ...");
			emmaOrchestrator.tell(new EmmaOrchestrator.Start(emmaOrchestrator.getClass().getSimpleName()), ActorRef.noSender());
			logger.info(method_name + "Sent :)");
			
	 }
	 
 }
