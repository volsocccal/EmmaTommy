package emmaTommy.EmmaTommyDataConverter.ActorsMessages;

import akka.actor.ActorRef;

public class StartConsuming {    
	protected ActorRef dataConverterActor;
	public StartConsuming(ActorRef dataConverterActor) {
    	super();
    	if (dataConverterActor == null) {
    		throw new NullPointerException("Received dataConverterActor was null");
    	}
    	this.dataConverterActor = dataConverterActor;
    }
	public ActorRef getDataConverterActor() {
		return this.dataConverterActor;
	}
}
