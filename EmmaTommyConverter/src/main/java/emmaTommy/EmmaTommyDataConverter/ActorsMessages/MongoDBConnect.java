package emmaTommy.EmmaTommyDataConverter.ActorsMessages;

public class MongoDBConnect {

	protected String persCollectionName;
	protected String wipCollectionName;
	public MongoDBConnect(String persCollectionName, String wipCollectionName) {
		this.persCollectionName = persCollectionName;
		this.wipCollectionName = wipCollectionName;		
	}
	public String getPersCollectionName() {
		return this.persCollectionName;
	}
	public String getWipCollectionName() {
		return this.wipCollectionName;
	}
}
