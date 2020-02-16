package emmaTommy.EmmaTommyConverter.Actors;

import akka.actor.typed.PostStop;
import emmaTommy.EmmaDataModel.Missione;
import emmaTommy.EmmaDataModel.Factories.MissioneFactory;
import emmaTommy.EmmaTommyDataConverter.ActorsMessages.MissioniDataJSON;
import emmaTommy.EmmaTommyDataConverter.ActorsMessages.ServizioDataJSON;
import emmaTommy.EmmaTommyDataConverter.ActorsMessages.StartConversion;
import emmaTommy.TommyDataModel.Servizio;
import emmaTommy.TommyDataModel.Factories.ServizioFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;


import org.apache.logging.log4j.LogManager;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;


public class EmmaTommyJsonConverter extends AbstractActor {
	
	protected org.apache.logging.log4j.Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
	
	protected MissioneFactory mFact;
	protected ServizioFactory sFact;
	protected Boolean saveJSONToLog;
	protected Boolean saveJSONToFile;
	protected String json_folder_path;
	protected Boolean sendJSONOverKAFKA;
	protected ActorRef JsonKafkaProducer;
	
	public static Props props(String text, String confPath) {
        return Props.create(EmmaTommyJsonConverter.class, text, confPath);
    }

	private EmmaTommyJsonConverter(String confPath) {
		
		// Logger Method Name
		String method_name = "::EmmaTommyJsonConverter(): ";
		
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
		
		// Load Configuration Data
		this.saveJSONToLog = (Integer.parseInt(prop.getProperty("saveJSONToLog")) == 1) ? (true) : (false);
		this.saveJSONToFile = (Integer.parseInt(prop.getProperty("saveJSONToFile")) == 1) ? (true) : (false);
		this.sendJSONOverKAFKA = (Integer.parseInt(prop.getProperty("sendJSONOverKAFKA")) == 1) ? (true) : (false);
		this.json_folder_path = prop.getProperty("json_folder_path");
		
		//
		this.sendJSONOverKAFKA = false;
		
		// Create Missione Factory
		this.mFact = new MissioneFactory(); 
		
		// Create Servizio Factory
		this.sFact = new ServizioFactory(); 
		
	}
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(StartConversion.class, this::onStartConversion)
				.match(MissioniDataJSON.class, this::onParse)
				.match(PostStop.class, signal -> onPostStop())
				.match(String.class, s -> {
					logger.info(this.getClass().getSimpleName() + " Received String message: {}", s);
	             })
				.matchAny(o -> logger.warn(this.getClass().getSimpleName() + " received unknown message"))
				.build();
	}
	
	protected void onStartConversion(StartConversion startConv) {
		// Logger Method Name
		String method_name = "::onStartConversion(): ";
		logger.info(method_name + "Received Start Converting Event");
		if (startConv.getKafkaProducerActor() != null) {
			JsonKafkaProducer = startConv.getKafkaProducerActor();
			this.sendJSONOverKAFKA = this.sendJSONOverKAFKA && startConv.getSendOverKafka();
		}
	}
	
	protected void onParse(MissioniDataJSON parseData) {
	    
		// Logger Method Name
		String method_name = "::onParse(): ";
		
		// Check if the received data is valid
		logger.trace(method_name + "Got new Missione JSON");
		if (!parseData.getValidData()) {
			logger.error(method_name + "Something is wrong in the received data - " + parseData.getErrorMsg());	
		}
		
		// Unmarshall the received JSON into a Missione Object
		
		try {
			
			logger.trace(method_name + "Parsing Missione JSON " + parseData.getID());
			
			// Build Missione
			Missione m = this.mFact.buildMissioneUnmarshallJSON(parseData.getJSON());
			
			// Convert from Missione to Servizio
			Servizio s = this.sFact.buildServizio(m);
			
			// Convert Servizio to JSON
			String servizio_JSON = s.toJSON();
			
			// Validate Servizio for Sending over to Tommy
			ArrayList<String> errors = s.validateForStoring();
			int codiceMissione = Integer.parseInt(s.getCodiceServizio());
			
			if (this.sendJSONOverKAFKA) {
				ServizioDataJSON jsonServizio = new ServizioDataJSON(codiceMissione, servizio_JSON, errors.isEmpty(), errors);
				JsonKafkaProducer.tell(jsonServizio, this.getSelf());
			}
			
			String missione_JSON = s.toJSON();
			if (this.sendJSONOverKAFKA) {
				MissioniDataJSON jsonMissioni = new MissioniDataJSON(codiceMissione, missione_JSON);
				JsonKafkaProducer.tell(jsonMissioni, this.getSelf());
			}
			if (this.saveJSONToLog) {
				logger.info(method_name + "Missione id=" + codiceMissione);
				logger.trace(missione_JSON);
			}
			if (this.saveJSONToFile) {
				this.writeJSONToFile(this.json_folder_path, missione_JSON, codiceMissione);
			}
			
			
		} catch (Exception e) {
			logger.error(method_name + e.getClass().getSimpleName() + " - " + e.getMessage());
		}	
		
	}
	
	protected void writeJSONToFile(String folderPath, String missione_JSON, int codiceMissione) throws IOException {
		FileOutputStream outputStream = new FileOutputStream(folderPath + "/" + codiceMissione + ".json");
        outputStream.write(missione_JSON.getBytes());     
        outputStream.close();
	}
	
	protected void onPostStop() {
		String method_name = "::onPostStop(): ";
		logger.info(method_name + "Received Stop Event");		
		
	}

}
