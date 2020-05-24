package emmaTommy.DBClient.ActorsMessages.Replies;

public class CollectionFound extends Reply {
	protected String collectionName;
	public CollectionFound(String collectionName) {
		super();
		if (collectionName == null) {
			throw new NullPointerException("Received collectionName was null");
		}
		if (collectionName.isBlank()) {
			throw new IllegalArgumentException("Received collectionName was blanck");
		}
		this.collectionName = collectionName;
	}
	public String getCollectionName() {
		return this.collectionName;
	}
}
