package emmaTommy.DBapi.Queries;

public class moveServizioByIDTable extends moveServizioByID {
	public moveServizioByIDTable(String ID, String oldTableName, String newTableName) {
		super(ID, oldTableName, newTableName);
	}
	public String GetOldTableName() {
		return this.oldSection;
	}

	public String GetNewTableName() {
		return this.newSection;
	}
}