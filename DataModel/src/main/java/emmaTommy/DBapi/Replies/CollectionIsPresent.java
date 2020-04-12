package emmaTommy.DBapi.Replies;

public class CollectionIsPresent extends Reply {
	protected String collection;
	protected Boolean presentFlag;
	public CollectionIsPresent(String collection, Boolean presentFlag) {
		super();
		if (collection == null) {
			throw new NullPointerException("Received collection was null");
		}
		if (collection.isBlank()) {
			throw new IllegalArgumentException("Received collection was blanck");
		}
		this.collection = collection;
		this.presentFlag = presentFlag;
	}
	public String getCollection() {
		return this.collection;
	}
	public Boolean getIsCollectionPresent() {
		return this.presentFlag;
	}
}
