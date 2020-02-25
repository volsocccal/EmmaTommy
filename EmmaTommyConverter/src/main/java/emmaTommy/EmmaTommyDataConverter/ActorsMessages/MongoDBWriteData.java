package emmaTommy.EmmaTommyDataConverter.ActorsMessages;

public class MongoDBWriteData {
	private int id;
	private String collectionName;
	private String json;
	public MongoDBWriteData(int id, String collectionName, String json) {
		this.id = id;
		if (collectionName == null) {
			this.collectionName = "";
		} else {
			this.collectionName = collectionName;
		}
		if (json == null) {
			this.json = "";
		} else {
			this.json = json;
		}
	}
	public int getID() {
		return this.id;
	}
	public String getCollectionName() {
		return this.collectionName;
	}
	public String getJSON() {
		return this.json;
	}
}
