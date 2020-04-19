package emmaTommy.DBAbstraction.ActorsMessages.Queries;

public class Query {
	protected String queryTypeName = this.getClass().getSimpleName();
	public Query () {
	}
	public String getQueryTypeName() {
		return this.queryTypeName;
	}
}
