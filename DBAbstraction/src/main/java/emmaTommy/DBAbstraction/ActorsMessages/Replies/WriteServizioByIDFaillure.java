package emmaTommy.DBAbstraction.ActorsMessages.Replies;

public class WriteServizioByIDFaillure extends DBOperationFaillure {
	protected String ID;
	protected String newServizioJSON;
	protected String collectionName;
	public WriteServizioByIDFaillure(String cause, String ID, String newServizioJSON, String collectionName) {
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
		if (newServizioJSON == null) {
			throw new NullPointerException("Received newServizioJSON was null");
		}
		this.newServizioJSON = newServizioJSON;
	}
	public String getID() {
		return this.ID;
	}
	public String getNewServizioJSON() {
		return this.newServizioJSON;
	}
	public String getCollectionName() {
		return this.collectionName;
	}
}
