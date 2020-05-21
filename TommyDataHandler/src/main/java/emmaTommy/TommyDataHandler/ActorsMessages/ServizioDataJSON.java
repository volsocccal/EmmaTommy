package emmaTommy.TommyDataHandler.ActorsMessages;


public class ServizioDataJSON {
		
	protected Boolean validData;
	protected String errorMsg;
	protected int ID;
	protected final String json;
    
	
	public ServizioDataJSON(int ID, String json) {
    	super();
    	this.validData = false;
        this.errorMsg = "";
    	this.ID = ID;
    	this.json = json;
    	this.validateData();
    }
    
    public Boolean validateData() {
    	validData = true;
    	if (json == null) {
    		this.validData = false;
    		this.errorMsg = "JSON Data was NULL";
    	} else if (json.isEmpty()) {
    		this.validData = false;
    		this.errorMsg = "JSON Data was Empty";
    	}
    	if (json.isBlank()) {
    		this.validData = false;
    		this.errorMsg = "JSON Data was Blanck";
    	}
    	return this.validData;
    }
    
    public String getErrorMsg() {
		return this.errorMsg;
	}
    
    public Boolean getValidData( ) {
    	return this.validData;
    }
    
    public int getID() {
    	return this.ID;
    }
    
    public String getJSON() {
    	return this.json;
    }

}
