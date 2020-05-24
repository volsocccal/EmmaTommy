package emmaTommy.DBClient.ActorsMessages.Replies;

public class ReplyServizioById extends Reply {
	protected String ID;
	protected String servizioJSON;
	protected String collectionName;
	public ReplyServizioById(String ID, String servizioJSON, String collectionName) {
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
		if (servizioJSON == null) {
			throw new NullPointerException("Received servizioJSON was null");
		}
		this.servizioJSON = servizioJSON;
	}
	public String getID() {
		return this.ID;
	}
	public String getServizioJSON() {
		return this.servizioJSON;
	}
	public String getCollectionName() {
		return this.collectionName;
	}
	
}
