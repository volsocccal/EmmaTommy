package emmaTommy.DBapi.Queries;

import emmaTommy.TommyDataModel.TommyEnrichedJSON;

public class WriteNewServizioByID extends Query {
	protected String ID;
	protected TommyEnrichedJSON newServizio;
	public WriteNewServizioByID(String ID, TommyEnrichedJSON newServizio) {
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
	}
	public String getID() {
		return this.ID;
	}
	public TommyEnrichedJSON getNewServizio() {
		return this.newServizio;
	}
}
