package emmaTommy.TommyDataHandler.ActorsMessages;

import java.util.ArrayList;

public class PostDataResponse extends PostData {

	protected Boolean success;
	protected String tommyResponse;
	
	public PostDataResponse(PostData postData, Boolean success, String tommyResponse) {		
		this(postData.getCodiceMezzo(), postData.getCodiciServizi(), postData.getJsonServizi(),success, tommyResponse);		
	}
	
	public PostDataResponse(String codiceMezzo, ArrayList<Integer> codiciServizi, String jsonServizi, Boolean success, String tommyResponse) {
		super(codiceMezzo, codiciServizi, jsonServizi);
		String method_name = "::PostDataResponse(): ";
		
		// Success Flag
		this.tommyResponse = tommyResponse;
		
		// tommyResponse
		if (tommyResponse == null) {
			throw new NullPointerException(method_name + "tommyResponse was nullptr");
		}
		if (codiceMezzo.isBlank()) {
			throw new IllegalArgumentException(method_name + "tommyResponse was blanck");
		}
		this.tommyResponse = tommyResponse;
		
	}

	public Boolean getSuccess() {
		return success;
	}
	
	public String getTommyResponse() {
		return tommyResponse;
	}
	
}
