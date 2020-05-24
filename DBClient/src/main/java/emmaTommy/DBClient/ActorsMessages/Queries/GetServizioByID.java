package emmaTommy.DBClient.ActorsMessages.Queries;

public class GetServizioByID extends Query {
	protected String ID;
	protected String collectionName;
	public GetServizioByID(String callingActorName, String callingActorID, String ID, String collectionName) {
		super(callingActorName, callingActorID);
		if (ID == null) {
			throw new NullPointerException("Received ID was null");
		}
		if (ID.isBlank()) {
			throw new IllegalArgumentException("Received ID was blanck");
		}
		this.ID = ID;
		if (collectionName == null) {
			throw new NullPointerException("Received collectionName was null");
		}
		if (collectionName.isBlank()) {
			throw new IllegalArgumentException("Received collectionName was blanck");
		}
		this.collectionName = collectionName;
	}
	public String getID() {
		return this.ID;
	}
	public String getCollectionName() {
		return this.collectionName;
	}
}
