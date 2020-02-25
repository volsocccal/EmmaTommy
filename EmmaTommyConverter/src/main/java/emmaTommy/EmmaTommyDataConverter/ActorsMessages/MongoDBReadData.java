package emmaTommy.EmmaTommyDataConverter.ActorsMessages;

import java.util.ArrayList;

public class MongoDBReadData {
	private ArrayList<Integer> ids;
	private String collectionName;
	public MongoDBReadData(String collectionName) {
		this.ids = new ArrayList<Integer>();
		if (collectionName == null) {
			this.collectionName = "";
		} else {
			this.collectionName = collectionName;
		}
	}
	public MongoDBReadData(int id, String collectionName) {
		this.ids = new ArrayList<Integer>();
		this.ids.add(id);
		if (collectionName == null) {
			this.collectionName = "";
		} else {
			this.collectionName = collectionName;
		}
	}
	public MongoDBReadData(ArrayList<Integer> ids, String collectionName) {
		ids = new ArrayList<Integer>();
		if (ids != null) {
			for (Integer id: ids) {
				this.ids.add(id);
			}
		}
		if (collectionName == null) {
			this.collectionName = "";
		} else {
			this.collectionName = collectionName;
		}
	}
	public ArrayList<Integer> getIDs() {
		return this.ids;
	}
	public String getCollectionName() {
		return this.collectionName;
	}
}
