package emmaTommy.DBServerAbstraction.DBExceptions;

public class DBOperationFailedException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public DBOperationFailedException() {
		// TODO Auto-generated constructor stub
	}

	public DBOperationFailedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public DBOperationFailedException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public DBOperationFailedException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public DBOperationFailedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}
	
}
