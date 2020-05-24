package emmaTommy.DBClient.ActorsMessages.Replies;

public class DBIsAlreadyLocked extends DBFailedToBeLocked {

	protected String lockOwnerName;
	protected String lockOwnerID;
	public DBIsAlreadyLocked(String lockOwnerName, String lockOwnerID) {
		super("The DB was already locked");
		if (lockOwnerName == null) {
			throw new NullPointerException("Received lockOwner was nullptr");
		}
		if (lockOwnerName.isBlank()) {
			throw new IllegalArgumentException("Received lockOwner is Blanck");
		}
		if (lockOwnerID == null) {
			throw new NullPointerException("Received lockOwnerID was nullptr");
		}
		if (lockOwnerID.isBlank()) {
			throw new IllegalArgumentException("Received lockOwnerID is Blanck");
		}
		this.lockOwnerName = lockOwnerName;
		this.lockOwnerID = lockOwnerID;
		this.cause = this.cause + " by Actor " + this.lockOwnerName + "  ID " + this.lockOwnerID;
	}
	public String getLockOwnerName() {
		return this.lockOwnerName;
	}
	public String getLockOwnerID() {
		return this.lockOwnerID;
	}

}
