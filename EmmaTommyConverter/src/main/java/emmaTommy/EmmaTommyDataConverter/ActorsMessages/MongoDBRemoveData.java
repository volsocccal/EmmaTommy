package emmaTommy.EmmaTommyDataConverter.ActorsMessages;

public class MongoDBRemoveData {
	private int id;
	private String collectionName;
	public MongoDBRemoveData(int id, String collectionName) {
		this.id = id;
		if (collectionName == null) {
			this.collectionName = "";
		} else {
			this.collectionName = collectionName;
		}
	}
	public int getID() {
		return this.id;
	}
	public String getCollectionName() {
		return this.collectionName;
	}
}
