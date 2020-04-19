package emmaTommy.DBAbstraction.ActorsMessages.Replies;

public class UpdateServizioByIDSuccess extends Reply {
	protected String ID;
	protected String oldServizioJSON;
	protected String updatedServizioJSON;
	protected String collectionName;
	public UpdateServizioByIDSuccess(String ID, String oldServizioJSON, String updatedServizioJSON, String collectionName) {
		super();
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
		if (oldServizioJSON == null) {
			throw new NullPointerException("Received oldServizioJSON was null");
		}
		this.oldServizioJSON = oldServizioJSON;
		if (updatedServizioJSON == null) {
			throw new NullPointerException("Received updatedServizioJSON was null");
		}
		this.updatedServizioJSON = updatedServizioJSON;
	}
	public String getID() {
		return this.ID;
	}
	public String getOldServizioJSON() {
		return this.oldServizioJSON;
	}
	public String getUpdatedServizioJSON() {
		return this.updatedServizioJSON;
	}
	public String getCollectionName() {
		return this.collectionName;
	}
}
