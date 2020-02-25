package emmaTommy.EmmaParser.Actors;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;

import akka.actor.typed.PostStop;
import akka.actor.AbstractActor;
import akka.actor.Props;

import emmaTommy.EmmaParser.ActorsMessages.MissioniDataJSON;

public class EmmaJSONProducer extends AbstractActor {

	protected org.apache.logging.log4j.Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
	protected Properties KafkaProducerProps;
	protected String topic;
	protected KafkaProducer<Integer, String> kafkaProducer;
	
	
	public static Props props(String text, String confPath) {
        return Props.create(EmmaJSONProducer.class, text, confPath);
    }

	private EmmaJSONProducer(String confPath) throws FileNotFoundException {
		
		// Logger Method Name
		String method_name = "::EmmaJSONProducer(): ";
		
		// Define and Load Configuration File
		this.KafkaProducerProps = new Properties();
		logger.trace(method_name + "Loading Properties FileName: " + confPath);
		FileInputStream fileStream = null;
		try {
			fileStream = new FileInputStream(confPath);
		} catch (FileNotFoundException e) {
			logger.fatal(method_name + e.getMessage());
			throw new FileNotFoundException(e.getMessage());			
		}
		try {
			this.KafkaProducerProps.load(fileStream);
		    logger.trace(method_name + this.KafkaProducerProps.toString());
		} catch (IOException e) {
			logger.fatal(e.getMessage());
			throw new FileNotFoundException(e.getMessage());	
		}
		
		// Read Topic Property
		this.topic = this.KafkaProducerProps.getProperty("topic");
		this.KafkaProducerProps.remove("topic");
		
		// Create Kafka Producer
		this.kafkaProducer = new KafkaProducer<Integer, String> (this.KafkaProducerProps);
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(MissioniDataJSON.class, this::onSend)
				.match(PostStop.class, signal -> onPostStop())
				.match(String.class, s -> {
					logger.info(this.getClass().getSimpleName() + " Received String message: {}", s);
	             })
				.matchAny(o -> logger.warn(this.getClass().getSimpleName() + " received unknown message"))
				.build();
	}

	protected void onSend(MissioniDataJSON jsonData) {
	    
		// Logger Method Name
		String method_name = "::onSend(): ";
		
		// Check if the received data is valid
		logger.info(method_name + "Got new Missione JSON - ID=" + jsonData.getID());
		if (!jsonData.validData) {
			logger.error(method_name + "Something is wrong in the received data - " + jsonData.getErrorMsg());	
		}
		
		// Send Over Kafka
		try {
			logger.info(method_name + "Writing missione with ID= " + jsonData.getID() + " over topic " + this.topic);	
			ProducerRecord<Integer, String> kafkaProducerRecord = new ProducerRecord<Integer, String>(this.topic, jsonData.getID(), jsonData.getJSON());
			this.kafkaProducer.send(kafkaProducerRecord);		
		} catch (Exception e) {
			logger.error(method_name + e.getClass().getSimpleName() + " - " + e.getMessage());
		}
		
	}
	
	
	protected void onPostStop() {
		
		// Logger Method Name
		String method_name = "::onPostStop(): ";
		logger.info(method_name + "Received Stop Event");
		
		// Stop Kafka Producer
		logger.info(method_name + "Stopping Kafka Producer ...");
		this.kafkaProducer.close();
		logger.info(method_name + "Stopped Kafka Producer Successfully");
		
	}
	

}

