package emmaTommy.DBAbstraction.ActorsMessages.Replies;

public class DBIsNotAlive extends DBOperationFaillure {

	public DBIsNotAlive(String cause) {
		super(cause);
	}

}
