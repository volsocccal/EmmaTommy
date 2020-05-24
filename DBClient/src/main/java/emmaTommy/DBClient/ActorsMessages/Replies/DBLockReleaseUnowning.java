package emmaTommy.DBClient.ActorsMessages.Replies;

public class DBLockReleaseUnowning extends DBLockReleaseFaillure {
	protected String lockOwnerName;
	protected String lockOwnerID;
	public DBLockReleaseUnowning(String lockOwnerName, String lockOwnerID) {
		super("The Client did not own the Lock");
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
		this.cause = this.cause + ", it is owned by " + this.lockOwnerName + "  ID " + this.lockOwnerID;;
	}
	public String getLockOwnerName() {
		return this.lockOwnerName;
	}
	public String getLockOwnerID() {
		return this.lockOwnerID;
	}
}
