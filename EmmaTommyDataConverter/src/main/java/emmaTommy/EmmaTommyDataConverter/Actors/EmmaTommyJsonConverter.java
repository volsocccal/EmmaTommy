package emmaTommy.EmmaTommyDataConverter.Actors;

import akka.actor.typed.PostStop;
import emmaTommy.EmmaDataModel.Missione;
import emmaTommy.EmmaDataModel.Factories.MissioneFactory;
import emmaTommy.EmmaTommyDataConverter.ActorsMessages.MissioniDataJSON;
import emmaTommy.EmmaTommyDataConverter.ActorsMessages.ServizioDataJSON;
import emmaTommy.TommyDataModel.Servizio;
import emmaTommy.TommyDataModel.Factories.ServizioFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class EmmaTommyJsonConverter extends AbstractActor {
	
	protected org.apache.logging.log4j.Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
	
	protected MissioneFactory mFact;
	protected ServizioFactory sFact;

	
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
		// ...
		
		// Create Missione Factory
		this.mFact = new MissioneFactory(); 
		
		// Create Servizio Factory
		this.sFact = new ServizioFactory(); 
		
	}
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(MissioniDataJSON.class, this::onParse)
				.match(PostStop.class, signal -> onPostStop())
				.build();
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
		
		
	}
	
	protected void onPostStop() {
		String method_name = "::onPostStop(): ";
		logger.info(method_name + "Received Stop Event");
	}

}
