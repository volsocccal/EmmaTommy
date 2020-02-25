package emmaTommy.EmmaTommyDataConverter.ActorsMessages;

import akka.actor.ActorRef;

public class StartConsuming {    
	protected ActorRef dataConverterActor;
    protected ActorRef mongoHandlerActor;
    protected Boolean sendOverMongo;
    protected String missioniCollectionName;
	public StartConsuming(ActorRef dataConverterActor, ActorRef mongoHandlerActor, Boolean sendOverMongo, String missioniCollectionName) {
    	super();
    	this.dataConverterActor = dataConverterActor;
    	this.mongoHandlerActor = mongoHandlerActor;
    	this.sendOverMongo = sendOverMongo;
    	if (missioniCollectionName == null) {
    		this.missioniCollectionName = "";
    	} else {
    		this.missioniCollectionName = missioniCollectionName;
    	}
    }
	public ActorRef getDataConverterActor() {
		return this.dataConverterActor;
	}
	public ActorRef getMongoHandlerActor( ) {
		return this.mongoHandlerActor;
	}
	public Boolean getSendOverMongo() {
		return this.sendOverMongo;
	}
	public String getMissioniCollectionName() {
		return this.missioniCollectionName;
	}
}
