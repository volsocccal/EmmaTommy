package emmaTommy.DBapi.Replies;

import emmaTommy.TommyDataModel.TommyEnrichedJSON;

public class ReplyServizioById extends Reply {
	protected String ID;
	protected TommyEnrichedJSON servizio;
	public ReplyServizioById(String ID, TommyEnrichedJSON servizio) {
		super();
		if (ID == null) {
			throw new NullPointerException("Received ID was null");
		}
		if (ID.isBlank()) {
			throw new IllegalArgumentException("Received ID was blanck");
		}
		this.ID = ID;
		if (servizio == null) {
			throw new NullPointerException("Received servizio was null");
		}
		this.servizio = servizio;
	}
	public String getID() {
		return this.ID;
	}
	public TommyEnrichedJSON getServizio() {
		return this.servizio;
	}
	
}
