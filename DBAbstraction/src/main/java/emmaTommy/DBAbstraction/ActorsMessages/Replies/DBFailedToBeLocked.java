package emmaTommy.DBAbstraction.ActorsMessages.Replies;

public class DBFailedToBeLocked extends Reply {
	protected String cause;
	public DBFailedToBeLocked(String cause) {
		super();
		if (cause == null) {
			throw new NullPointerException("Received cause was null");
		}
		if (cause.isBlank()) {
			throw new IllegalArgumentException("Received cause was blanck");
		}
		this.cause = cause;
	}
	public String getCause() {
		return this.cause;
	}
}
