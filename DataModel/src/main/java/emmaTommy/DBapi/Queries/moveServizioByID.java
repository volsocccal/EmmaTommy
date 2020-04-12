package emmaTommy.DBapi.Queries;

public abstract class moveServizioByID extends Query {
	protected String ID;
	protected String oldCollection;
	protected String newCollection;
	public moveServizioByID(String ID, String oldCollection, String newCollection) {
		super();
		if (ID == null) {
			throw new NullPointerException("Received ID was null");
		}
		if (ID.isBlank()) {
			throw new IllegalArgumentException("Received ID was blanck");
		}
		this.ID = ID;
		if (oldCollection == null) {
			throw new NullPointerException("Received oldCollection was null");
		}
		if (oldCollection.isBlank()) {
			throw new IllegalArgumentException("Received oldCollection was blanck");
		}
		this.oldCollection = oldCollection;
		if (newCollection == null) {
			throw new NullPointerException("Received newCollection was null");
		}
		if (newCollection.isBlank()) {
			throw new IllegalArgumentException("Received newCollection was blanck");
		}
		this.newCollection = newCollection;
	}
	public String getID() {
		return this.ID;
	}
	protected String GetOldCollection() {
		return this.newCollection;
	}

	public String GetNewCollection() {
		return this.newCollection;
	}

}
