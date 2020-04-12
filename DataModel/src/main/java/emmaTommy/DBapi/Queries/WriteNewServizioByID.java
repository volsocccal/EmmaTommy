package emmaTommy.DBapi.Queries;

import emmaTommy.TommyDataModel.TommyEnrichedJSON;

public class WriteNewServizioByID extends Query {
	protected String ID;
	protected TommyEnrichedJSON newServizio;
	protected String collection;
	public WriteNewServizioByID(String ID, TommyEnrichedJSON newServizio, String collection) {
		super();
		if (ID == null) {
			throw new NullPointerException("Received ID was null");
		}
		if (ID.isBlank()) {
			throw new IllegalArgumentException("Received ID was blanck");
		}
		this.ID = ID;
		if (newServizio == null) {
			throw new NullPointerException("Received newServizio was null");
		}
		this.newServizio = newServizio;
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
	public TommyEnrichedJSON getNewServizio() {
		return this.newServizio;
	}
	public String getCollection() {
		return this.collection;
	}
}
