package emmaTommy.DBClient.ActorsMessages.Queries;

import java.util.TreeMap;

public class GetAllServiziInCollectionByProperties extends Query {
	protected String collectionName;
	protected TreeMap<ServizioQueryField, String> propNamesValuesMap;
	public GetAllServiziInCollectionByProperties(String callingActorName, String callingActorID, ServizioQueryField propName, String propValue, String collectionName) {
		super(callingActorName, callingActorID);
		if (collectionName == null) {
			throw new NullPointerException("Received collectionName was null");
		}
		if (collectionName.isBlank()) {
			throw new IllegalArgumentException("Received collectionName was blanck");
		}
		this.collectionName = collectionName;
		if (propName == null) {
			throw new NullPointerException("Received propName was null");
		}
		if (propValue == null) {
			throw new NullPointerException("Received propValue was null");
		}
		if (propValue.isBlank()) {
			throw new IllegalArgumentException("Received propValue was blanck");
		}
		this.propNamesValuesMap = new TreeMap<ServizioQueryField, String>();
		this.propNamesValuesMap.put(propName, propValue);
	}
	public GetAllServiziInCollectionByProperties(String callingActorName, String callingActorID, TreeMap<ServizioQueryField, String> propNamesValuesMap, String collectionName) {
		super(callingActorName, callingActorID);
		if (collectionName == null) {
			throw new NullPointerException("Received collectionName was null");
		}
		if (collectionName.isBlank()) {
			throw new IllegalArgumentException("Received collectionName was blanck");
		}
		this.collectionName = collectionName;
		this.propNamesValuesMap = new TreeMap<ServizioQueryField, String>();
		for (ServizioQueryField propName: propNamesValuesMap.keySet()) {
			if (propName == null) {
				throw new NullPointerException("Received propName was null");
			}
			String propValue = propNamesValuesMap.get(propName);
			if (propValue == null) {
				throw new NullPointerException("Received propValue was null");
			}
			if (propValue.isBlank()) {
				throw new IllegalArgumentException("Received propValue was blanck");
			}
			this.propNamesValuesMap.put(propName, propValue);
		}
	}
	public String getCollectionName() {
		return this.collectionName;
	}
	public TreeMap<ServizioQueryField, String> getPropertyNamesValuesMap() {
		return this.propNamesValuesMap;
	}
}