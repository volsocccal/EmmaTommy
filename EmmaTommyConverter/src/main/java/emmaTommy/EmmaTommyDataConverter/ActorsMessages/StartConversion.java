package emmaTommy.EmmaTommyDataConverter.ActorsMessages;

import akka.actor.ActorRef;

public class StartConversion {    
	protected ActorRef kafkaProducerActor;
	protected Boolean sendOverKafka;
	protected ActorRef mongoHandlerActor;
	protected Boolean sendOverMongo;
	protected String serviziCollectionName;
    public StartConversion(ActorRef kafkaProducerActor, Boolean sendOverKafka, ActorRef mongoHandlerActor, Boolean sendOverMongo, String serviziCollectionName) {
    	super();
    	this.kafkaProducerActor = kafkaProducerActor;
    	this.sendOverKafka = sendOverKafka;
    	this.mongoHandlerActor = mongoHandlerActor;
    	this.sendOverMongo = sendOverMongo;
    	if (serviziCollectionName == null) {
    		this.serviziCollectionName = "";
    	} else {
    		this.serviziCollectionName = serviziCollectionName;
    	}
    }
	public ActorRef getKafkaProducerActor() {
		return this.kafkaProducerActor;
	}
	public Boolean getSendOverKafka() {
		return this.sendOverKafka;
	}
	public ActorRef getMongoHandlerActor() {
		return this.mongoHandlerActor;
	}
	public Boolean getSendOverMongo() {
		return this.sendOverMongo;
	}
	public String getServiziCollectionName() {
		return this.serviziCollectionName;
	}
}
