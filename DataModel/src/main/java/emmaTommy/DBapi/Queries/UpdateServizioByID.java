package emmaTommy.DBapi.Queries;

import emmaTommy.TommyDataModel.TommyEnrichedJSON;

public class UpdateServizioByID extends Query {
	protected String ID;
	protected TommyEnrichedJSON updatedServizio;
	protected String collection;
	public UpdateServizioByID(String ID, TommyEnrichedJSON updatedServizio, String collection) {
		super();
		if (ID == null) {
			throw new NullPointerException("Received ID was null");
		}
		if (ID.isBlank()) {
			throw new IllegalArgumentException("Received ID was blanck");
		}
		this.ID = ID;
		if (updatedServizio == null) {
			throw new NullPointerException("Received updatedServizio was null");
		}
		this.updatedServizio = updatedServizio;
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
		return this.updatedServizio;
	}
	public String getCollection() {
		return this.collection;
	}
}
