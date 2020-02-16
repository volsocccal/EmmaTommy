package emmaTommy.EmmaTommyDataConverter.ActorsMessages;

import akka.actor.ActorRef;

public class StartConsuming {    
    private ActorRef dataConverterActor;
	public StartConsuming(ActorRef dataConverterActor) {
    	super();
    	this.dataConverterActor = dataConverterActor;
    }
	public ActorRef getDataConverterActor() {
		return this.dataConverterActor;
	}
}
