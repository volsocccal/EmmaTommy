package emmaTommy.DBServerAbstraction.DBExceptions;

public class CollectionNotPresentException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private String collectionName = "UnknownCollectionName";
	
	public CollectionNotPresentException() {
		// TODO Auto-generated constructor stub
	}

	public CollectionNotPresentException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	public CollectionNotPresentException(String message, String collectionName) {
		super(message);
		this.collectionName = collectionName;
	}

	public CollectionNotPresentException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public CollectionNotPresentException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public CollectionNotPresentException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public String getCollectionName() {
		return this.collectionName;
	}
	
}
