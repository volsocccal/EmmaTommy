package emmaTommy.EmmaParser.Actors;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.logging.log4j.LogManager;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.typed.PostStop;
import emmaTommy.EmmaDataModel.Missione;
import emmaTommy.EmmaDataModel.Missioni;
import emmaTommy.EmmaParser.ActorsMessages.MissioniDataJSON;
import emmaTommy.EmmaParser.ActorsMessages.MissioniDataXML;


public class EmmaXMLParserActor extends AbstractActor {
	
	protected org.apache.logging.log4j.Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
	
	protected int MISSIONE_MIN_ID;
	protected Boolean saveJSONToLog;
	protected Boolean saveJSONToFile;
	protected Boolean sendJSONOverKAFKA;	
	protected String json_folder_path;
		
	public static Props props(String text, String confPath) {
        return Props.create(EmmaXMLParserActor.class, text, confPath);
    }

	private EmmaXMLParserActor(String confPath) {
		
		// Logger Method Name
		String method_name = "::EmmaXMLParserActor(): ";
		
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
		this.MISSIONE_MIN_ID = Integer.parseInt(prop.getProperty("MISSIONE_MIN_ID"));		
		this.saveJSONToLog = (Integer.parseInt(prop.getProperty("saveJSONToLog")) == 1) ? (true) : (false);
		this.saveJSONToFile = (Integer.parseInt(prop.getProperty("saveJSONToFile")) == 1) ? (true) : (false);
		this.sendJSONOverKAFKA = (Integer.parseInt(prop.getProperty("sendJSONOverKAFKA")) == 1) ? (true) : (false);
		this.json_folder_path = prop.getProperty("json_folder_path");
		
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(MissioniDataXML.class, this::onParse)
				.match(PostStop.class, signal -> onPostStop())
				.build();
	}

	protected void onParse(MissioniDataXML parseData) {
	    
		// Logger Method Name
		String method_name = "::onParse(): ";
		
		// Check if the received data is valid
		logger.trace(method_name + "Got new Missioni XML");
		if (!parseData.validData) {
			logger.error(method_name + "Something is wrong in the received data - " + parseData.getErrorMsg());	
		}
		
		// Unmarshal
		try {
			this.parseXML(parseData.xml, parseData.producerJSONKAFKA);			
		} catch (Exception e) {
			logger.error(method_name + e.getClass().getSimpleName() + " - " + e.getMessage());
		}
		
	}

	protected void parseXML(String xml, ActorRef JsonKafkaProducer) throws JAXBException {
		
		// Logger Method Name
		String method_name = "::parseXML(): ";
		
		// Create String Reader
		StringReader xmlReader = new StringReader(xml);
		
		// Unmarshaller
		JAXBContext jaxbContext = JAXBContext.newInstance(Missioni.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Missioni m = (Missioni) jaxbUnmarshaller.unmarshal(xmlReader);
		if (m == null) {
			throw new NullPointerException("Unmarshalled Missioni Object was nullptr");
		}
		m.validateObject();
		
		// Check if the Unmarshalled Data contains missioni
		if (m.getMissioni() == null) {
			throw new NullPointerException("Unmarshalled Missioni List was nullptr");
		}
		if (m.getMissioni().isEmpty()) {
			throw new RuntimeException("Unmarshalled Missioni List was Empty");
		}
		logger.trace(method_name + "Got " +  m.getMissioni().size() + " new Missioni");
		for (int miss_num = 0; miss_num < m.getMissioni().size(); miss_num++) {
			Missione missione = m.getMissioni().get(miss_num);
			try {
				if (missione == null) {
					throw new NullPointerException("Missione at pos " + miss_num + "was NULL");
				}
				int codiceMissione = missione.getCodiceMissione();
				if (codiceMissione < MISSIONE_MIN_ID) {
					throw new RuntimeException("");
				}
				if (!missione.getValidState()) {
					logger.error(method_name + "missione " + codiceMissione + "was not valid. I will parse it for bookkeping");
				}
				String missione_JSON = missione.toJSON();
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
