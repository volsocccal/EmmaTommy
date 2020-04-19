package emmaTommy.DBAbstraction.ActorsMessages.Replies;

public class DBLockReleaseUnowning extends DBLockReleaseFaillure {
	protected String lockOwner; 
	public DBLockReleaseUnowning(String lockOwner) {
		super("The Client did not own the Lock");
		if (lockOwner == null) {
			throw new NullPointerException("Received lockOwner was null");
		}
		if (lockOwner.isBlank()) {
			throw new IllegalArgumentException("Received lockOwner was blank");
		}
		this.lockOwner = lockOwner;
		this.cause = this.cause + ", it is owned by " + lockOwner;
	}
	public String GetLockOwner() {
		return this.lockOwner;
	}

}
