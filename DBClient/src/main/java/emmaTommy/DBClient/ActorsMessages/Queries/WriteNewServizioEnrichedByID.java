package emmaTommy.DBClient.ActorsMessages.Queries;

import emmaTommy.TommyDataModel.TommyEnrichedJSON;

public class WriteNewServizioEnrichedByID extends Query {
	protected String servizioID;
	protected TommyEnrichedJSON newServizioEnrichedJSON;
	protected String collectionName;
	public WriteNewServizioEnrichedByID(String callingActorName, String callingActorID, String servizioID, TommyEnrichedJSON newServizioEnrichedJSON, String collectionName) {
		super(callingActorName, callingActorID);
		if (servizioID == null) {
			throw new NullPointerException("Received servizioID was null");
		}
		if (servizioID.isBlank()) {
			throw new IllegalArgumentException("Received servizioID was blanck");
		}
		this.servizioID = servizioID;
		if (newServizioEnrichedJSON == null) {
			throw new NullPointerException("Received newServizioEnrichedJSON was null");
		}
		if (newServizioEnrichedJSON.getCodiceServizio().compareTo(this.servizioID) != 0) {
			throw new IllegalArgumentException("Received servizioID was " + servizioID + " but the received servizio had ID " + newServizioEnrichedJSON.getCodiceServizio());
		}
		this.newServizioEnrichedJSON = newServizioEnrichedJSON;
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
	public TommyEnrichedJSON getNewServizioEnrichedJSON() {
		return this.newServizioEnrichedJSON;
	}
	public String getCollectionName() {
		return this.collectionName;
	}
}
