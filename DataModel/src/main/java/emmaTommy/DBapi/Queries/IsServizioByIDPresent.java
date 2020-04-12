package emmaTommy.DBapi.Queries;

public class IsServizioByIDPresent extends Query {
	protected String ID;
	protected String collection;
	public IsServizioByIDPresent(String ID, String collection) {
		super();
		if (ID == null) {
			throw new NullPointerException("Received ID was null");
		}
		if (ID.isBlank()) {
			throw new IllegalArgumentException("Received ID was blanck");
		}
		this.ID = ID;
		if (collection == null) {
			throw new NullPointerException("Received collection was null");
		}
		if (collection.isBlank()) {
			throw new IllegalArgumentException("Received collection was blanck");
		}
		this.collection = collection;
	}
	public String getID() {
		return this.ID;
	}
	public String getCollection() {
		return this.collection;
	}
}
