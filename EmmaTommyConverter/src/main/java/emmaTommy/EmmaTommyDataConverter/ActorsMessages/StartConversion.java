package emmaTommy.EmmaTommyDataConverter.ActorsMessages;

import akka.actor.ActorRef;

public class StartConversion {    
	private ActorRef kafkaProducerActor;
	private Boolean sendOverKafka;
    public StartConversion(ActorRef kafkaProducerActor, Boolean sendOverKafka) {
    	super();
    	this.kafkaProducerActor = kafkaProducerActor;
    	this.sendOverKafka = sendOverKafka;
    }
	public ActorRef getKafkaProducerActor() {
		return kafkaProducerActor;
	}
	public Boolean getSendOverKafka() {
		return this.sendOverKafka;
	}
}
