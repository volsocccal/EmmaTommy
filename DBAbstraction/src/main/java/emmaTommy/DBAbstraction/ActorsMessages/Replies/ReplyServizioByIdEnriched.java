package emmaTommy.DBAbstraction.ActorsMessages.Replies;

import emmaTommy.TommyDataModel.TommyEnrichedJSON;

public class ReplyServizioByIdEnriched extends Reply {
	protected String ID;
	protected TommyEnrichedJSON enrichedServizio;
	protected String collectionName;
	public ReplyServizioByIdEnriched(String ID, TommyEnrichedJSON enrichedServizio, String collectionName) {
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
		if (enrichedServizio == null) {
			throw new NullPointerException("Received servizio was null");
		}
		this.enrichedServizio = enrichedServizio;
	}
	public String getID() {
		return this.ID;
	}
	public TommyEnrichedJSON getEnrichedServizio() {
		return this.enrichedServizio;
	}
	public String getCollectionName() {
		return this.collectionName;
	}
	
}
