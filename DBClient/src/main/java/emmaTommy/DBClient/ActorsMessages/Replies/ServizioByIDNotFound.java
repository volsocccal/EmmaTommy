package emmaTommy.DBClient.ActorsMessages.Replies;

public class ServizioByIDNotFound extends DBOperationFaillure {
	protected String ID;
	protected String collectionName;
	public ServizioByIDNotFound(String ID, String collectionName) {
		super("Servizio Not Found");
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
		this.cause = "Servizio " + ID + " Not Found In Collection " + collectionName;
		
	}
	public String getID() {
		return this.ID;
	}
	public String getCollectionName() {
		return this.collectionName;
	}
}
