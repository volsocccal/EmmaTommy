package emmaTommy.TommyHandler.ActorsMessages;

import java.util.ArrayList;

public class PostData {

	protected String codiceMezzo;
	protected ArrayList<Integer> codiciServizi;
	protected String jsonServizi;
	
	public PostData(String codiceMezzo, ArrayList<Integer> codiciServizi, String jsonServizi) {
		String method_name = "::PostData(): ";
		
		// Codice Mezzo
		if (codiceMezzo == null) {
			throw new NullPointerException(method_name + "codiceMezzo was nullptr");
		}
		if (codiceMezzo.isBlank()) {
			throw new IllegalArgumentException(method_name + "codiceMezzo was blanck");
		}
		this.codiceMezzo = codiceMezzo;
		
		// Codici Servizi
		if (codiciServizi == null) {
			throw new NullPointerException(method_name + "codiciServizi was nullptr");
		}
		if (codiciServizi.isEmpty()) {
			throw new IllegalArgumentException(method_name + "codiciServizi was empty");
		}
		this.codiciServizi = new ArrayList<Integer>();
		for (int codiceServizio: codiciServizi) {
			this.codiciServizi.add(codiceServizio);
		}		
		
		// Json Servizi
		if (jsonServizi == null) {
			throw new NullPointerException(method_name + "jsonServizi was nullptr");
		}
		if (jsonServizi.isBlank()) {
			throw new IllegalArgumentException(method_name + "jsonServizi was blanck");
		}
		this.jsonServizi = jsonServizi;
	}

	public String getCodiceMezzo() {
		return codiceMezzo;
	}

	public ArrayList<Integer> getCodiciServizi() {
		return codiciServizi;
	}

	public String getJsonServizi() {
		return jsonServizi;
	}
	
}
