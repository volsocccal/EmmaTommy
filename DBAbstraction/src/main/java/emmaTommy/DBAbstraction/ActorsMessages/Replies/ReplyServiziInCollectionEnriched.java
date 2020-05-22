package emmaTommy.DBAbstraction.ActorsMessages.Replies;

import java.util.HashMap;
import emmaTommy.TommyDataModel.TommyEnrichedJSON;

public class ReplyServiziInCollectionEnriched extends Reply {

	protected String collectionName;
	HashMap<String, TommyEnrichedJSON> serviziMap;
	public ReplyServiziInCollectionEnriched(HashMap<String, TommyEnrichedJSON> serviziMap, String collectionName) {
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
		this.serviziMap = new HashMap<String, TommyEnrichedJSON>();
		for (String servizioID: serviziMap.keySet()) {
			if (servizioID == null) {
				throw new NullPointerException("Found a null key in serviziMap");
			}
			TommyEnrichedJSON servizioEnriched = serviziMap.get(servizioID);
			if (servizioEnriched == null) {
				throw new NullPointerException("Found a null servizio associated to ID " +  servizioID + " in serviziMap");
			}
			this.serviziMap.put(servizioID, servizioEnriched);
		}
	}
	public String getCollectionName() {
		return this.collectionName;
	}
	public HashMap<String, TommyEnrichedJSON> getServiziMap() {
		return this.serviziMap;
	}

}
