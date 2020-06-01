package emmaTommy.TommyPoster.ActorsMessages;

import java.util.ArrayList;

public class PostDataResponse extends PostData {

	public static enum PostResponseStatus {
		SUCCESS,
		WARNING,
		ERROR
	}
	protected PostResponseStatus status;
	protected String tommyResponse;
	
	public PostDataResponse(PostData postData, PostResponseStatus status, String tommyResponse) {		
		this(postData.getCodiceMezzo(), postData.getCodiciServizi(), postData.getJsonServizi(), status, tommyResponse);		
	}
	
	public PostDataResponse(String codiceMezzo, ArrayList<String> codiciServizi, String jsonServizi, PostResponseStatus status, String tommyResponse) {
		super(codiceMezzo, codiciServizi, jsonServizi);
		String method_name = "::PostDataResponse(): ";
		
		// Success Flag
		this.status = status;
		
		// tommyResponse
		if (tommyResponse == null) {
			throw new NullPointerException(method_name + "tommyResponse was nullptr");
		}
		if (codiceMezzo.isBlank()) {
			throw new IllegalArgumentException(method_name + "tommyResponse was blanck");
		}
		this.tommyResponse = tommyResponse;
		
	}

	public PostResponseStatus getPostResponseStatus() {
		return status;
	}
	
	public String getPostResponseStatusStr() {
		return status.name();
	}
	
	public Boolean isResponseStatusSuccess() {
		return this.status == PostResponseStatus.SUCCESS;
	}
	
	public Boolean isResponseStatusWarning() {
		return this.status == PostResponseStatus.WARNING;
	}
	
	public Boolean isResponseStatusError() {
		return this.status == PostResponseStatus.ERROR;
	}
	
	public String getTommyResponse() {
		return tommyResponse;
	}
	
}
