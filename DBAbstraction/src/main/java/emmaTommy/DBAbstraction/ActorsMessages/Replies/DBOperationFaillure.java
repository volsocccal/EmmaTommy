package emmaTommy.DBAbstraction.ActorsMessages.Replies;

public abstract class DBOperationFaillure extends Reply {
	protected String cause;
	DBOperationFaillure(String cause) {
		if (cause == null) {
			throw new NullPointerException("Received Cause was nullptr");
		}
		if (cause.isBlank()) {
			throw new IllegalArgumentException("Received Cause is Blanck");
		}
		this.cause = cause;
	}
	public String getCause() {
		return this.cause;
	}
}
