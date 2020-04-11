package emmaTommy.TommyHandler.ActorsMessages;

import akka.actor.ActorRef;

public class StartConsuming {    
    protected ActorRef mySqlHandlerActor;
    protected Boolean sendOverMySql;
	public StartConsuming(ActorRef mySqlHandlerActor, Boolean sendOverMySql) {
    	super();
    	this.mySqlHandlerActor = mySqlHandlerActor;
    	this.sendOverMySql = sendOverMySql;
    }
	public ActorRef getMySqlHandlerActor( ) {
		return this.mySqlHandlerActor;
	}
	public Boolean getSendOverMySql() {
		return this.sendOverMySql;
	}
}
