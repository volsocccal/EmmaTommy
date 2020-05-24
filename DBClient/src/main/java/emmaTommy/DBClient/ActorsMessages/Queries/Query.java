package emmaTommy.DBClient.ActorsMessages.Queries;

public class Query {
	protected String queryTypeName = this.getClass().getSimpleName();
	protected String callingActorName;
	protected String callingActorID;
	public Query (String callingActorName, String callingActorID) {
		if (callingActorName == null) {
    		throw new NullPointerException("Received null callingActorName");
    	}
    	if (callingActorName.isBlank()) {
    		throw new IllegalArgumentException("Received blanck callingActorName");
    	}
    	if (callingActorID == null) {
    		throw new NullPointerException("Received null callingActorID");
    	}
    	if (callingActorID.isBlank()) {
    		throw new IllegalArgumentException("Received blanck callingActorID");
    	}
    	this.callingActorName = callingActorName;
    	this.callingActorID = callingActorID;
	}
	public String getQueryTypeName() {
		return this.queryTypeName;
	}
	public String getCallingActorName() {
		return this.callingActorName;
	}
	public String getCallingActorID() {
		return this.callingActorID;
	}
}
