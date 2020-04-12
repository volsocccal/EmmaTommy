package emmaTommy.DBapi.Queries;

import emmaTommy.TommyDataModel.TommyEnrichedJSON;

public class UpdateServizioByID extends Query {
	protected String ID;
	protected TommyEnrichedJSON updatedServizio;
	public UpdateServizioByID(String ID, TommyEnrichedJSON updatedServizio) {
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
	}
	public String getID() {
		return this.ID;
	}
	public TommyEnrichedJSON getNewServizio() {
		return this.updatedServizio;
	}
}
