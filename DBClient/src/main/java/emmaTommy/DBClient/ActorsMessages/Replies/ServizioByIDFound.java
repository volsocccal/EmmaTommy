package emmaTommy.DBClient.ActorsMessages.Replies;

public class ServizioByIDFound extends Reply {
	protected String ID;
	protected String collectionName;
	public ServizioByIDFound(String ID, String collectionName) {
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
	public String GetCollectionName() {
		return this.collectionName;
	}
}
