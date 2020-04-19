package emmaTommy.DBAbstraction.ActorsMessages.Replies;

public class CollectionNotFound extends DBOperationFaillure {
	protected String collectionName;
	public CollectionNotFound(String collectionName) {
		super("Collection not Found");
		if (collectionName == null) {
			throw new NullPointerException("Received collectionName was null");
		}
		if (collectionName.isBlank()) {
			throw new IllegalArgumentException("Received collectionName was blanck");
		}
		this.collectionName = collectionName;
		this.cause = "Collection " + collectionName + " Not Found";
	}
	public String getCollectionName() {
		return this.collectionName;
	}

}
