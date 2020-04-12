package emmaTommy.TommyDataHandler.ActorsMessages;

import akka.actor.ActorRef;

public class StartConsuming {
	private ActorRef dataWriterRef;
	private Boolean sendToDataWriter;
	public StartConsuming(ActorRef DataWriter, Boolean sendToDataWriter) {
		if (DataWriter == null)
			throw new NullPointerException("StartConsuming(): Received null DataWriter Actor Ref");
		this.dataWriterRef = DataWriter;
		this.sendToDataWriter = sendToDataWriter;
    }
	public ActorRef getDataWriterActorRef() {
		return this.dataWriterRef;
	}
	public Boolean getSendToDataWriter() {
		return this.sendToDataWriter;
	}
}
