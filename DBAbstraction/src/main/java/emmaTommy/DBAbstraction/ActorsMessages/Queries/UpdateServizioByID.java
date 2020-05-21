package emmaTommy.DBAbstraction.ActorsMessages.Queries;

public class UpdateServizioByID extends Query {
	protected String servizioID;
	protected String updatedServizioJSON;
	protected String collectionName;
	public UpdateServizioByID(String callingActorName, String callingActorID, String servizioID, String updatedServizioJSON, String collectionName) {
		super(callingActorName, callingActorID);
		if (servizioID == null) {
			throw new NullPointerException("Received servizioID was null");
		}
		if (servizioID.isBlank()) {
			throw new IllegalArgumentException("Received servizioID was blanck");
		}
		this.servizioID = servizioID;
		if (updatedServizioJSON == null) {
			throw new NullPointerException("Received updatedServizioJSON was null");
		}
		this.updatedServizioJSON = updatedServizioJSON;
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
	public String getUpdatedServizioJSON() {
		return this.updatedServizioJSON;
	}
	public String getCollectionName() {
		return this.collectionName;
	}
}
