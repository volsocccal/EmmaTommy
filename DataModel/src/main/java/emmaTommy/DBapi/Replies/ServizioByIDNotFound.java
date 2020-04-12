package emmaTommy.DBapi.Replies;

public class ServizioByIDNotFound extends Reply {
	protected String ID;
	public ServizioByIDNotFound(String ID) {
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
