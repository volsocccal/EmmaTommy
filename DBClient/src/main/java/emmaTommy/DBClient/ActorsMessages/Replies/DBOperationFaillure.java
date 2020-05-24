package emmaTommy.DBClient.ActorsMessages.Replies;

public class DBOperationFaillure extends Reply {
	protected String cause;
	public DBOperationFaillure(String cause) {
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
