package emmaTommy.DBapi.Replies;

public class DBIsAlreadyLocked extends Reply {
	protected String lockOwner;
	public DBIsAlreadyLocked(String lockOwner) {
		super();
		if (lockOwner == null) {
			throw new NullPointerException("Received lockOwner was null");
		}
		if (lockOwner.isBlank()) {
			throw new IllegalArgumentException("Received lockOwner was blanck");
		}
		this.lockOwner = lockOwner;
	}
	public String getLockOwner() {
		return this.lockOwner;
	}
}
