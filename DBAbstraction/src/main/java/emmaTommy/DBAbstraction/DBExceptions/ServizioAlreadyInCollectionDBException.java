package emmaTommy.DBAbstraction.DBExceptions;

public class ServizioAlreadyInCollectionDBException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String servizioID = "UnknownServizioID";
	private String collectionName = "UnknownCollectionName";
	
	public ServizioAlreadyInCollectionDBException() {
		// TODO Auto-generated constructor stub
	}

	public ServizioAlreadyInCollectionDBException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ServizioAlreadyInCollectionDBException(String message, String servizioID, String collectionName) {
		super(message);
		this.servizioID = servizioID;
		this.collectionName = collectionName;
	}
	
	public ServizioAlreadyInCollectionDBException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public ServizioAlreadyInCollectionDBException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ServizioAlreadyInCollectionDBException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public String getCollectionName() {
		return this.collectionName;
	}
	
	public String getServizioID() {
		return this.servizioID;
	}
	
	
}
