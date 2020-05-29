package emmaTommy.DBClient.ActorsMessages.Queries;

import emmaTommy.TommyDataModel.TommyEnrichedJSON;

public class UpdateServizioEnrichedByID extends Query {
	protected String servizioID;
	protected TommyEnrichedJSON updatedServizioEnrichedJSON;
	protected String collectionName;
	public UpdateServizioEnrichedByID(String callingActorName, String callingActorID, String servizioID, TommyEnrichedJSON updatedServizioEnrichedJSON, String collectionName) {
		super(callingActorName, callingActorID);
		if (servizioID == null) {
			throw new NullPointerException("Received servizioID was null");
		}
		if (servizioID.isBlank()) {
			throw new IllegalArgumentException("Received servizioID was blanck");
		}
		this.servizioID = servizioID;
		if (updatedServizioEnrichedJSON == null) {
			throw new NullPointerException("Received updatedServizioEnrichedJSON was null");
		}
		if (updatedServizioEnrichedJSON.getCodiceServizio().compareTo(this.servizioID) != 0) {
			throw new IllegalArgumentException("Received servizioID was " + servizioID + " but the received servizio had ID " + updatedServizioEnrichedJSON.getCodiceServizio());
		}
		this.updatedServizioEnrichedJSON = updatedServizioEnrichedJSON;
		if (collectionName == null) {
			throw new NullPointerException("Received collectionName was null");
		}
		if (collectionName.isBlank()) {
			throw new IllegalArgumentException("Received collectionName was blanck");
		}
		this.collectionName = collectionName;
	}
	public String getServizioID() {
		return this.servizioID;
	}
	public TommyEnrichedJSON getUpdatedServizioEnrichedJSON() {
		return this.updatedServizioEnrichedJSON;
	}
	public String getCollectionName() {
		return this.collectionName;
	}
}
