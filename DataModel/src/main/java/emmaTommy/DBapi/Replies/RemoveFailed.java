package emmaTommy.DBapi.Replies;

public class RemoveFailed extends Reply {
	protected String cause;
	protected String ID;
	public RemoveFailed(String ID, String cause) {
		super();
		if (ID == null) {
			throw new NullPointerException("Received ID was null");
		}
		if (ID.isBlank()) {
			throw new IllegalArgumentException("Received ID was blanck");
		}
		this.ID = ID;
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
	public String getID() {
		return this.ID;
	}
}
