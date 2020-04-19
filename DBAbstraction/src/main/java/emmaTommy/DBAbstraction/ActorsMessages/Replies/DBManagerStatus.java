package emmaTommy.DBAbstraction.ActorsMessages.Replies;

public abstract class DBManagerStatus extends Reply {
	protected String status;
	DBManagerStatus (String status) {
		if (status == null) {
			throw new NullPointerException("Received null status");
		}
		if (status.isBlank()) {
			throw new IllegalArgumentException("Received blanck status");
		}
		this.status = status;
	}
	public String getStatus() {
		return this.status;
	}
}
