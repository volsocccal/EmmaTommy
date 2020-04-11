package emmaTommy.TommyDataHandler.ActorsMessages;

import java.util.ArrayList;

public class ServizioDataJSON {
		
	protected Boolean validData;
	protected ArrayList<String> errorMsg;
	protected int ID;
	protected final String json;
    
	
	public ServizioDataJSON(int ID, String json, Boolean validData) {
    	super();
    	this.validData = false;
        this.errorMsg = new ArrayList<String>();
    	this.ID = ID;
    	this.json = json;
    	this.validateData();
    }
	
    public ServizioDataJSON(int ID, String json, Boolean validData, String errorMsg) {
    	super();
    	this.validData = false;
        this.errorMsg = new ArrayList<String>();
        this.errorMsg.add(errorMsg);
    	this.ID = ID;
    	this.json = json;
    	this.validateData();
    }
    
    public ServizioDataJSON(int ID, String json, Boolean validData, ArrayList<String> errorMsg) {
    	super();
    	this.validData = false;
        this.errorMsg = new ArrayList<String>();
        if (errorMsg != null) {
        	this.errorMsg.addAll(errorMsg);
        }
    	this.ID = ID;
    	this.json = json;
    	this.validateData();
    }
    
    public Boolean validateData() {
    	if (json == null) {
    		this.validData = false;
    		this.errorMsg.add("JSON Data was NULL");
    	}
    	if (json.isEmpty()) {
    		this.validData = false;
    		this.errorMsg.add("JSON Data was Empty");
    	}
    	if (json.isBlank()) {
    		this.validData = false;
    		this.errorMsg.add("JSON Data was Blanck");
    	}
    	return this.validData;
    }
    
    public ArrayList<String> getErrorMsgList() {
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
