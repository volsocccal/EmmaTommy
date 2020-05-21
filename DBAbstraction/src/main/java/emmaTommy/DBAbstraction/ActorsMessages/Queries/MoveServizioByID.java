package emmaTommy.DBAbstraction.ActorsMessages.Queries;

public class MoveServizioByID extends Query {
	protected String servizioID;
	protected String oldCollectionName;
	protected String newCollectionName;
	public MoveServizioByID(String callingActorName, String callingActorID, String servizioID, String oldCollectionName, String newCollectionName) {
		super(callingActorName, callingActorID);
		if (servizioID == null) {
			throw new NullPointerException("Received servizioID was null");
		}
		if (servizioID.isBlank()) {
			throw new IllegalArgumentException("Received servizioID was blanck");
		}
		this.servizioID = servizioID;
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
	public String getServizioID() {
		return this.servizioID;
	}
	public String GetOldCollectionName() {
		return this.oldCollectionName;
	}

	public String GetNewCollectionName() {
		return this.newCollectionName;
	}

}
