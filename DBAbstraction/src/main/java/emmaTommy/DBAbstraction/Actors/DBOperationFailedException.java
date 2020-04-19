package emmaTommy.DBAbstraction.Actors;

public class DBOperationFailedException extends Exception {

	public DBOperationFailedException(String string) {
		super(string);
	}

	private static final long serialVersionUID = 3356571836006464083L;

}
