package emmaTommy.DBClient.ActorsMessages.Replies;

public class DBIsNotAlive extends DBOperationFaillure {

	public DBIsNotAlive(String cause) {
		super(cause);
	}

}
