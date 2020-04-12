package emmaTommy.DBapi.Queries;

public abstract class moveServizioByID extends Query {
	protected String ID;
	protected String oldSection;
	protected String newSection;
	public moveServizioByID(String ID, String oldSection, String newSection) {
		super();
		if (ID == null) {
			throw new NullPointerException("Received ID was null");
		}
		if (ID.isBlank()) {
			throw new IllegalArgumentException("Received ID was blanck");
		}
		this.ID = ID;
		if (oldSection == null) {
			throw new NullPointerException("Received oldSection was null");
		}
		if (oldSection.isBlank()) {
			throw new IllegalArgumentException("Received oldSection was blanck");
		}
		this.oldSection = oldSection;
		if (newSection == null) {
			throw new NullPointerException("Received newSection was null");
		}
		if (newSection.isBlank()) {
			throw new IllegalArgumentException("Received newSection was blanck");
		}
		this.newSection = newSection;
	}
	public String getID() {
		return this.ID;
	}
	protected String GetOldSection() {
		return this.oldSection;
	}

	public String GetNewSection() {
		return this.newSection;
	}

}
