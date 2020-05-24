package emmaTommy.DBClient.ActorsMessages.Queries;

public class AcquireDBLock extends Query {
	public AcquireDBLock(String callingActorName, String callingActorID) {
		super(callingActorName, callingActorID);
	}
}
