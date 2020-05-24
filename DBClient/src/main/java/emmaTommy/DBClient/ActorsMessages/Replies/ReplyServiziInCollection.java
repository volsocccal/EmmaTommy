package emmaTommy.DBClient.ActorsMessages.Replies;

import java.util.HashMap;

public class ReplyServiziInCollection extends Reply {

	protected String collectionName;
	HashMap<String, String> serviziMap;
	public ReplyServiziInCollection(HashMap<String, String> serviziMap, String collectionName) {
		if (collectionName == null) {
			throw new NullPointerException("Received collectionName was null");
		}
		if (collectionName.isBlank()) {
			throw new IllegalArgumentException("Received collectionName was blanck");
		}
		this.collectionName = collectionName;
		if (serviziMap == null) {
			throw new NullPointerException("Received serviziMap was null");
		}
		this.serviziMap = new HashMap<String, String>();
		for (String servizioID: serviziMap.keySet()) {
			if (servizioID == null) {
				throw new NullPointerException("Found a null key in serviziMap");
			}
			String servizio = serviziMap.get(servizioID);
			if (servizio == null) {
				throw new NullPointerException("Found a null servizio associated to ID " +  servizioID + " in serviziMap");
			}
			if (servizio.isBlank()) {
				throw new NullPointerException("Found a blanck servizio associated to ID " +  servizioID + " in serviziMap");
			}
			this.serviziMap.put(servizioID, servizio);
		}
	}
	public String getCollectionName() {
		return this.collectionName;
	}
	public HashMap<String, String> getServiziMap() {
		return this.serviziMap;
	}

}