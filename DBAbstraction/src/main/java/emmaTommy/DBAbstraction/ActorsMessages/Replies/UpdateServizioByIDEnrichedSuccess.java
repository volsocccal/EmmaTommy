package emmaTommy.DBAbstraction.ActorsMessages.Replies;

import emmaTommy.TommyDataModel.TommyEnrichedJSON;

public class UpdateServizioByIDEnrichedSuccess extends Reply {
	protected String ID;
	protected TommyEnrichedJSON oldServizioEnriched;
	protected TommyEnrichedJSON updatedServizioEnriched;
	protected String collectionName;
	public UpdateServizioByIDEnrichedSuccess(String ID, 
			TommyEnrichedJSON oldServizioEnriched, 
			TommyEnrichedJSON updatedServizioEnriched, 
			String collectionName) {
		super();
		if (ID == null) {
			throw new NullPointerException("Received ID was null");
		}
		if (ID.isBlank()) {
			throw new IllegalArgumentException("Received ID was blanck");
		}
		this.ID = ID;
		if (collectionName == null) {
			throw new NullPointerException("Received collectionName was null");
		}
		if (collectionName.isBlank()) {
			throw new IllegalArgumentException("Received collectionName was blanck");
		}
		this.collectionName = collectionName;
		if (oldServizioEnriched == null) {
			throw new NullPointerException("Received oldServizioEnriched was null");
		}
		this.oldServizioEnriched = oldServizioEnriched;
		if (updatedServizioEnriched == null) {
			throw new NullPointerException("Received updatedServizioEnriched was null");
		}
		this.updatedServizioEnriched = updatedServizioEnriched;
	}
	public String getID() {
		return this.ID;
	}
	public TommyEnrichedJSON getOldServizioEnriched() {
		return this.oldServizioEnriched;
	}
	public TommyEnrichedJSON getUpdatedServizioEnriched() {
		return this.updatedServizioEnriched;
	}
	public String getCollectionName() {
		return this.collectionName;
	}
}
