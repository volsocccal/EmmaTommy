package emmaTommy.EmmaParser.ActorsMessages;

public abstract class MissioniData {
	
	public Boolean validData;
	public String errorMsg;
	
	 public MissioniData() {
	      this.validData = false;
	      this.errorMsg = "Data Not Yet Validated";
	    }
	    
	    public abstract Boolean validateData();
	    
		public String getErrorMsg() {
			return this.errorMsg;
		}

}
