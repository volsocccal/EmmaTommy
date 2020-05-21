package emmaTommy.DBAbstraction.ActorsMessages.Queries;

public class IsDBLocked extends Query {
	public IsDBLocked(String callingActorName, String callingActorID) {
		super(callingActorName, callingActorID);
	}
}
