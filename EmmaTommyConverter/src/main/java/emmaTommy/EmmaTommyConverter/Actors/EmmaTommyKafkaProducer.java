package emmaTommy.EmmaTommyConverter.Actors;

import akka.actor.typed.PostStop;
import emmaTommy.EmmaTommyDataConverter.ActorsMessages.ServizioDataJSON;
import emmaTommy.EmmaTommyDataConverter.ActorsMessages.StartProducing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;

import akka.actor.AbstractActor;
import akka.actor.Props;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class EmmaTommyKafkaProducer extends AbstractActor {
	
	protected org.apache.logging.log4j.Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
	
	protected int kafkaPollingTime; 
	protected String topic;
	protected Properties KafkaProducerProps;
	protected Producer<Integer, String> kafkaProducer;
	
	protected Boolean produce;
	protected Boolean sendOverToKafka;
	
	public static Props props(String text, String confPath) {
        return Props.create(EmmaTommyKafkaProducer.class, text, confPath);
    }

	private EmmaTommyKafkaProducer(String confPath) throws FileNotFoundException {
		
		// Logger Method Name
		String method_name = "::EmmaTommyKafkaProducer(): ";
		
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
		this.sendOverToKafka = (Integer.parseInt(this.KafkaProducerProps.getProperty("sendOverToKafka")) == 1) ? (true) : (false);
		this.KafkaProducerProps.remove("topic");
		this.KafkaProducerProps.remove("sendOverToKafka");
		
		// Create Kafka Producer
		this.kafkaProducer = new KafkaProducer<Integer, String> (this.KafkaProducerProps);
		
		// Set Conversion cicle to false
		this.produce = false;
		
	}
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(StartProducing.class, this::onStart)
				.match(ServizioDataJSON.class, this::onProduce)
				.match(PostStop.class, signal -> onPostStop())
				.match(String.class, s -> {
					logger.info(this.getClass().getSimpleName() + " Received String message: {}", s);
	             })
				.matchAny(o -> logger.warn(this.getClass().getSimpleName() + " received unknown message"))
				.build();
	}
	
	protected void onStart(StartProducing startProd) {
		
		// Logger Method Name
		String method_name = "::onStart(): ";
		logger.info(method_name + "Received Start Producing Event");
		
		// Set Producer Flag to True
		this.produce = true;			
		
	}
	
	protected void onProduce(ServizioDataJSON jsonData) {
	    
		// Logger Method Name
		String method_name = "::onProduce(): ";
		
		if (!this.produce) {
			logger.error("I didn't receive yet the StartProducingMessage, discarding servizio " + jsonData.getID());
		}
		
		// Check if the received data is valid
		logger.info(method_name + "Got new Servizio JSON - ID=" + jsonData.getID());
		if (!jsonData.validateData()) {
			logger.error(method_name + "Something is wrong in the received data - " + jsonData.getErrorMsgList());	
		}
		
		// Send Over Kafka
		try {
			if (this.sendOverToKafka) {
				logger.info(method_name + "Writing missione with ID= " + jsonData.getID() + " over topic " + this.topic);	
				ProducerRecord<Integer, String> kafkaProducerRecord = new ProducerRecord<Integer, String>(this.topic, jsonData.getID(), jsonData.getJSON());
				this.kafkaProducer.send(kafkaProducerRecord);		
			} else {
				logger.error(method_name + "Writing disabled by conf for missione with ID= " + jsonData.getID() + " over topic " + this.topic);	
			}
		} catch (Exception e) {
			logger.error(method_name + e.getClass().getSimpleName() + " - " + e.getMessage());
		}
		
	}
	
	
	
	protected void onPostStop() {
		String method_name = "::onPostStop(): ";
		logger.info(method_name + "Received Stop Event");		
		logger.info(method_name + "Closing Kafka Producer");
		this.produce = false; 
		this.kafkaProducer.close();
		
		
		
	}

}
