package emmaTommy.DBAbstraction.ActorsMessages.Replies;

public class DBIsNotAlive extends DBOperationFaillure {

	DBIsNotAlive(String cause) {
		super(cause);
	}

}
