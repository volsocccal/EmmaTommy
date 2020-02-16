package emmaTommy.EmmaTommyDataConverter.ActorsMessages;

public class MissioniDataJSON {
		
	protected Boolean validData;
	protected String errorMsg;
	protected int ID;
	protected final String json;
    
    public MissioniDataJSON(int ID, String json) {
    	super();
    	this.validData = false;
        this.errorMsg = "Data Not Yet Validated";
    	this.ID = ID;
    	this.json = json;
    	this.validateData();
    }
    
    public Boolean validateData() {
    	this.validData = true;
    	if (json == null) {
    		this.validData = false;
    		this.errorMsg = "JSON Data was NULL";
    	}
    	if (json.isEmpty()) {
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
