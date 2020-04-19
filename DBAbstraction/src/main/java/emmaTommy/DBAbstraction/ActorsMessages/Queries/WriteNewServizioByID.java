package emmaTommy.DBAbstraction.ActorsMessages.Queries;

import emmaTommy.TommyDataModel.TommyEnrichedJSON;

public class WriteNewServizioByID extends Query {
	protected String ID;
	protected String newServizioJSON;
	protected String collectionName;
	public WriteNewServizioByID(String ID, String newServizioJSON, String collectionName) {
		super();
		if (ID == null) {
			throw new NullPointerException("Received ID was null");
		}
		if (ID.isBlank()) {
			throw new IllegalArgumentException("Received ID was blanck");
		}
		this.ID = ID;
		if (newServizioJSON == null) {
			throw new NullPointerException("Received newServizioJSON was null");
		}
		this.newServizioJSON = newServizioJSON;
		if (collectionName == null) {
			throw new NullPointerException("Received collectionName was null");
		}
		if (collectionName.isBlank()) {
			throw new IllegalArgumentException("Received collectionName was blanck");
		}
		this.collectionName = collectionName;
	}
	public String getID() {
		return this.ID;
	}
	public String getNewServizioJSON() {
		return this.newServizioJSON;
	}
	public String getCollection() {
		return this.collectionName;
	}
}
