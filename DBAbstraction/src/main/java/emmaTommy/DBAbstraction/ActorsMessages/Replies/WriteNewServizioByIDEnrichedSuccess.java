package emmaTommy.DBAbstraction.ActorsMessages.Replies;

import emmaTommy.TommyDataModel.TommyEnrichedJSON;

public class WriteNewServizioByIDEnrichedSuccess extends Reply {
	protected String ID;
	protected TommyEnrichedJSON newServizioEnriched;
	protected String collectionName;
	public WriteNewServizioByIDEnrichedSuccess(String ID, TommyEnrichedJSON newServizioEnriched, String collectionName) {
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
		if (newServizioEnriched == null) {
			throw new NullPointerException("Received newServizioEnriched was null");
		}
		this.newServizioEnriched = newServizioEnriched;
	}
	public String getID() {
		return this.ID;
	}
	public TommyEnrichedJSON getNewServizioEnriched() {
		return this.newServizioEnriched;
	}
	public String getCollectionName() {
		return this.collectionName;
	}
}
