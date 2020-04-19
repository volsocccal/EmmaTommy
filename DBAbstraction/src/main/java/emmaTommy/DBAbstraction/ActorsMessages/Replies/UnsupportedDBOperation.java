package emmaTommy.DBAbstraction.ActorsMessages.Replies;

public class UnsupportedDBOperation extends DBOperationFaillure {
	protected String unsupportedOperationType;
	UnsupportedDBOperation(String unsupportedOperationType) {
		super("The Received Operation wasn't supported");
		if (unsupportedOperationType == null) {
			throw new NullPointerException("Received unsupportedOperationType was nullptr");
		}
		if (unsupportedOperationType.isBlank()) {
			throw new IllegalArgumentException("Received unsupportedOperationType is Blanck");
		}
		this.unsupportedOperationType = unsupportedOperationType;
		this.cause = this.cause + ": " + this.unsupportedOperationType;
	}
	public String getUnsupportedOperationType() {
		return this.unsupportedOperationType;
	}
}
