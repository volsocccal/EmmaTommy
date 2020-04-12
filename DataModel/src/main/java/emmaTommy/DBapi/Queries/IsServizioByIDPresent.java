package emmaTommy.DBapi.Queries;

public class IsServizioByIDPresent extends Query {
	protected String ID;
	public IsServizioByIDPresent(String ID) {
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
