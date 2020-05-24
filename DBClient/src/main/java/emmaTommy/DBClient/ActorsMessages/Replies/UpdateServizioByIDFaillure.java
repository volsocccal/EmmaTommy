package emmaTommy.DBClient.ActorsMessages.Replies;

public class UpdateServizioByIDFaillure extends DBOperationFaillure {
	protected String ID;
	protected String updatedServizioJSON;
	protected String collectionName;
	public UpdateServizioByIDFaillure(String cause, String ID, String updatedServizioJSON, String collectionName) {
		super(cause);
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
		if (updatedServizioJSON == null) {
			throw new NullPointerException("Received updatedServizioJSON was null");
		}
		this.updatedServizioJSON = updatedServizioJSON;
	}
	public String getID() {
		return this.ID;
	}
	public String getUpdatedServizioJSON() {
		return this.updatedServizioJSON;
	}
	public String getCollectionName() {
		return this.collectionName;
	}
}
