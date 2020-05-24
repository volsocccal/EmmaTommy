package emmaTommy.DBClient.ActorsMessages.Queries;

public class RemoveServizioByID extends Query {
	protected String servizioID;
	protected String collectionName;
	public RemoveServizioByID(String callingActorName, String callingActorID, String servizioID, String collectionName) {
		super(callingActorName, callingActorID);
		if (servizioID == null) {
			throw new NullPointerException("Received servizioID was null");
		}
		if (servizioID.isBlank()) {
			throw new IllegalArgumentException("Received servizioID was blanck");
		}
		this.servizioID = servizioID;
		if (collectionName == null) {
			throw new NullPointerException("Received collectionName was null");
		}
		if (collectionName.isBlank()) {
			throw new IllegalArgumentException("Received collectionName was blanck");
		}
		this.collectionName = collectionName;
	}
	public String getServizioID() {
		return this.servizioID;
	}
	public String getCollectionName() {
		return this.collectionName;
	}
}
