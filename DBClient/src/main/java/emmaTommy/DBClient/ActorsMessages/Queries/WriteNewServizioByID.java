package emmaTommy.DBClient.ActorsMessages.Queries;

public class WriteNewServizioByID extends Query {
	protected String servizioID;
	protected String newServizioJSON;
	protected String collectionName;
	public WriteNewServizioByID(String callingActorName, String callingActorID, String servizioID, String newServizioJSON, String collectionName) {
		super(callingActorName, callingActorID);
		if (servizioID == null) {
			throw new NullPointerException("Received servizioID was null");
		}
		if (servizioID.isBlank()) {
			throw new IllegalArgumentException("Received servizioID was blanck");
		}
		this.servizioID = servizioID;
		if (newServizioJSON == null) {
			throw new NullPointerException("Received newServizioJSON was null");
		}
		this.newServizioJSON = newServizioJSON;
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
	public String getNewServizioJSON() {
		return this.newServizioJSON;
	}
	public String getCollectionName() {
		return this.collectionName;
	}
}
