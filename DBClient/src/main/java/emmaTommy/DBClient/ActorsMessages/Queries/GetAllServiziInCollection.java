package emmaTommy.DBClient.ActorsMessages.Queries;

public class GetAllServiziInCollection extends Query {
	protected String collectionName;
	public GetAllServiziInCollection(String callingActorName, String callingActorID, String collectionName) {
		super(callingActorName, callingActorID);
		if (collectionName == null) {
			throw new NullPointerException("Received collectionName was null");
		}
		if (collectionName.isBlank()) {
			throw new IllegalArgumentException("Received collectionName was blanck");
		}
		this.collectionName = collectionName;
	}
	public String getCollectionName() {
		return this.collectionName;
	}
}
