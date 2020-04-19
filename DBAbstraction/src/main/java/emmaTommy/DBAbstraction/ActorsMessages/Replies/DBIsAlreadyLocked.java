package emmaTommy.DBAbstraction.ActorsMessages.Replies;

public class DBIsAlreadyLocked extends DBFailedToBeLocked {

	protected String lockOwner;
	DBIsAlreadyLocked(String lockOwner) {
		super("The DB was already locked");
		if (lockOwner == null) {
			throw new NullPointerException("Received lockOwner was nullptr");
		}
		if (lockOwner.isBlank()) {
			throw new IllegalArgumentException("Received lockOwner is Blanck");
		}
		this.lockOwner = lockOwner;
		this.cause = this.cause + " by Actor " + this.lockOwner;
	}
	public String getLockOwner() {
		return this.lockOwner;
	}

}
