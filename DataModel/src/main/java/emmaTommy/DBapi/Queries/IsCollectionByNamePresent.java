package emmaTommy.DBapi.Queries;

public class IsCollectionByNamePresent extends Query {
	protected String collection;
	public IsCollectionByNamePresent(String collection) {
		super();
		if (collection == null) {
			throw new NullPointerException("Received collection was null");
		}
		if (collection.isBlank()) {
			throw new IllegalArgumentException("Received collection was blanck");
		}
		this.collection = collection;
	}
	public String getCollection() {
		return this.collection;
	}
}
