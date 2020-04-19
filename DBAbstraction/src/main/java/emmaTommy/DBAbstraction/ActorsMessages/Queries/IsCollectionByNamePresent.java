package emmaTommy.DBAbstraction.ActorsMessages.Queries;

public class IsCollectionByNamePresent extends Query {
	protected String collectionName;
	public IsCollectionByNamePresent(String collectionName) {
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
