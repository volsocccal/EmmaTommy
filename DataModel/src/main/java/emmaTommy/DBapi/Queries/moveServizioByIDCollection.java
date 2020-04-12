package emmaTommy.DBapi.Queries;

public class moveServizioByIDCollection extends moveServizioByID {
	public moveServizioByIDCollection(String ID, String oldCollection, String newCollection) {
		super(ID, oldCollection, newCollection);
	}
	public String GetOldCollectionName() {
		return this.oldSection;
	}

	public String GetNewCollectionName() {
		return this.newSection;
	}
}
