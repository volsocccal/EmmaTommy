package emmaTommy.DBapi.Replies;

public class UpdateSuccess extends Reply {
	protected String ID;
	public UpdateSuccess(String ID) {
		super();
		if (ID == null) {
			throw new NullPointerException("Received ID was null");
		}
		if (ID.isBlank()) {
			throw new IllegalArgumentException("Received ID was blanck");
		}
		this.ID = ID;
	}
	public String getID() {
		return this.ID;
	}
}
