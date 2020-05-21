package emmaTommy.DBAbstraction.ActorsMessages.Queries;

public class IsDBAlive extends Query {
	public IsDBAlive(String callingActorName, String callingActorID) {
		super(callingActorName, callingActorID);
	}
}
