package emmaTommy.EmmaREST.ActorsMessages;

import akka.actor.ActorRef;

public final class StartREST {
    
	public ActorRef receiverXMLAKKA;
	public ActorRef producerJSONKAFKA;
	
    public StartREST(ActorRef receiverXMLAKKA, ActorRef producerJSONKAFKA) {    	
    	this.receiverXMLAKKA = receiverXMLAKKA;
    	this.producerJSONKAFKA = producerJSONKAFKA;
    }
	
}

