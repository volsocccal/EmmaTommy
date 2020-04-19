package emmaTommy.DBAbstraction.ActorsMessages.Replies;

public class MoveServizioByIDFaillure extends DBOperationFaillure {	
	protected String ID;
	protected String oldCollectionName;
	protected String newCollectionName;
	MoveServizioByIDFaillure(String cause, String ID, String oldCollectionName, String newCollectionName) {
		super(cause);
		if (ID == null) {
			throw new NullPointerException("Received ID was null");
		}
		if (ID.isBlank()) {
			throw new IllegalArgumentException("Received ID was blanck");
		}
		this.ID = ID;
		if (oldCollectionName == null) {
			throw new NullPointerException("Received oldCollectionName was null");
		}
		if (oldCollectionName.isBlank()) {
			throw new IllegalArgumentException("Received oldCollectionName was blanck");
		}
		this.oldCollectionName = oldCollectionName;
		if (newCollectionName == null) {
			throw new NullPointerException("Received newCollectionName was null");
		}
		if (newCollectionName.isBlank()) {
			throw new IllegalArgumentException("Received newCollectionName was blanck");
		}
		this.newCollectionName = newCollectionName;
	}
	public String getID() {
		return this.ID;
	}
	protected String GetOldCollectionName() {
		return this.oldCollectionName;
	}
	public String GetNewCollectionName() {
		return this.newCollectionName;
	}
}
