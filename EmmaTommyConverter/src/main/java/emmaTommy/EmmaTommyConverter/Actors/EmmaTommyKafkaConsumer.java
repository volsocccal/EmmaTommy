package emmaTommy.EmmaTommyConverter.Actors;

import akka.actor.typed.PostStop;
import emmaTommy.EmmaTommyDataConverter.ActorsMessages.Consume;
import emmaTommy.EmmaTommyDataConverter.ActorsMessages.MissioniDataJSON;
import emmaTommy.EmmaTommyDataConverter.ActorsMessages.StartConsuming;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.consumer.Consumer;

public class EmmaTommyKafkaConsumer extends AbstractActor {
	
	protected org.apache.logging.log4j.Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
	
	protected int kafkaPollingTime; 
	protected String topic;
	protected Properties KafkaConsumerProps;
	protected Consumer<Integer, String> kafkaConsumer;
	
	protected ActorRef dataConverterActor;
	protected Boolean convert;
	
	public static Props props(String text, String confPath) {
        return Props.create(EmmaTommyKafkaConsumer.class, text, confPath);
    }

	private EmmaTommyKafkaConsumer(String confPath) {
		
		// Logger Method Name
		String method_name = "::EmmaTommyKafkaConsumer(): ";
		
		// Define and Load Configuration File
		this.KafkaConsumerProps = new Properties();
		logger.trace(method_name + "Loading Properties FileName: " + confPath);
		FileInputStream fileStream = null;
		try {
			fileStream = new FileInputStream(confPath);
		} catch (FileNotFoundException e) {
			logger.fatal(method_name + e.getMessage());
		}
		try {
			this.KafkaConsumerProps.load(fileStream);
		    logger.trace(method_name + this.KafkaConsumerProps.toString());
		} catch (IOException e) {
			logger.fatal(method_name + e.getMessage());
		}
		
		// Load Configuration Data
		this.topic = this.KafkaConsumerProps.getProperty("topic");
		this.KafkaConsumerProps.remove("topic");
		this.kafkaPollingTime = Integer.parseInt(KafkaConsumerProps.getProperty("kafkaPollingTime"));
		this.KafkaConsumerProps.remove("kafkaPollingTime");
		
	    // Create the kafka consumer using props.
		try {
			logger.trace(method_name + "Creating Kafka Consumer");
			this.kafkaConsumer = new KafkaConsumer<>(this.KafkaConsumerProps);
			logger.trace(method_name + "Created Kafka Consumer");
		} catch (Exception e) {
			logger.error(method_name + "Failed to Create Kafka Consumer - " + e.getMessage());
		}
		
		// Set Conversion cicle to false
		this.convert = false;
		
	}
	
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(StartConsuming.class, this::onStart)
				.match(Consume.class, this::consume)
				.match(PostStop.class, signal -> onPostStop())
				.match(String.class, s -> {
					logger.info(this.getClass().getSimpleName() + " Received String message: {}", s);
	             })
				.matchAny(o -> logger.warn(this.getClass().getSimpleName() + " received unknown message"))
				.build();
	}
	
	protected void onStart(StartConsuming start) {
		
		// Logger Method Name
		String method_name = "::onStart(): ";
		logger.info(method_name + "Received Start Consuming Event");
		
		// Get Data Converter Actor
		this.dataConverterActor = start.getDataConverterActor();
		if (this.dataConverterActor == null)
		{
			this.convert = false;
		} else {
			this.convert = true;
		}
		
		this.kafkaConsumer.subscribe(Collections.singletonList(this.topic));
		logger.trace(method_name + "Subscribed to Kafka Topic: " + this.topic);
		this.self().tell(new Consume(), this.getSelf());
		
		
	}
	
	protected void consume(Consume cons) {
		String method_name = "::consume(): ";
		@SuppressWarnings("deprecation")
		final ConsumerRecords<Integer, String> consumerRecords = this.kafkaConsumer.poll(kafkaPollingTime);      	
		consumerRecords.forEach(record -> {
			logger.info(method_name + "Received new Missione: " + record.key());	
			if (this.convert) {
				this.dataConverterActor.tell(new MissioniDataJSON(record.key().intValue(), record.value()), this.getSelf());
				logger.info(method_name + "Sent Missione " + record.key() + " to " + this.dataConverterActor.path().name());	
			}
    	});
    	this.kafkaConsumer.commitAsync();            
    	this.getSelf().tell(new Consume(), this.getSelf());		
	}	
	
	protected void onPostStop() {
		String method_name = "::onPostStop(): ";
		logger.info(method_name + "Received Stop Event");		
		logger.info(method_name + "Closing Kafka Consumer");
		this.kafkaConsumer.close();
		this.convert = false; 
		
		
	}

}
