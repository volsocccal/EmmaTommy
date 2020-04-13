package emmaTommy.EmmaTommyDataConverter.ActorsMessages;

import akka.actor.ActorRef;

public class StartConversion {    
	protected ActorRef kafkaProducerActor;
	protected Boolean sendOverKafka;
    public StartConversion(ActorRef kafkaProducerActor, Boolean sendOverKafka) {
    	super();
    	this.kafkaProducerActor = kafkaProducerActor;
    	this.sendOverKafka = sendOverKafka;
    }
	public ActorRef getKafkaProducerActor() {
		return this.kafkaProducerActor;
	}
	public Boolean getSendOverKafka() {
		return this.sendOverKafka;
	}
}
