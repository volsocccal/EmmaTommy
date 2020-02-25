package emmaTommy.EmmaTommyDataConverter.ActorsMessages;

public class MongoDBMoveData {
	private int id;
	private String srcCollectionName;
	private String destCollectionName;
	public MongoDBMoveData(int id, String srcCollectionName, String destCollectionName) {
		this.id = id;
		if (srcCollectionName == null) {
			this.srcCollectionName = "";
		} else {
			this.srcCollectionName = srcCollectionName;
		}
		if (destCollectionName == null) {
			this.destCollectionName = "";
		} else {
			this.destCollectionName = destCollectionName;
		}
	}
	public int getID() {
		return this.id;
	}
	public String getSrcCollectionName() {
		return this.destCollectionName;
	}
	public String getDestCollectionName() {
		return this.destCollectionName;
	}
}
