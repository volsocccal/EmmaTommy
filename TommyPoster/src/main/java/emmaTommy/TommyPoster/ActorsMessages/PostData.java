package emmaTommy.TommyPoster.ActorsMessages;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import emmaTommy.TommyDataModel.TommyEnrichedJSON;

public class PostData {

	protected String codiceMezzo;
	protected ArrayList<String> codiciServizi;
	protected String jsonServizi;
	
	public PostData(String codiceMezzo, int codiceServizio, String jsonServizio) {
		this(codiceMezzo, Integer.toString(codiceServizio), jsonServizio);
		
	}
	
	public PostData(String codiceMezzo, String codiceServizio, String jsonServizio) {
		this(codiceMezzo, 
			 new ArrayList<String>() {
					private static final long serialVersionUID = 1L;
					{ 
			            add(codiceServizio); 
			        } 
		    	}, 
			 jsonServizio);
		
	}
	
	public PostData(String codiceMezzo, ArrayList<String> codiciServizi, ArrayList<String> jsonServiziList) {
		this(codiceMezzo, codiciServizi, unifyJSONS(jsonServiziList));
	}
	
	public PostData(String codiceMezzo, ArrayList<String> codiciServizi, String jsonServizi) {
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
		this.codiciServizi = new ArrayList<String>();
		for (String codiceServizio: codiciServizi) {
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

	public PostData(String codiceMezzo, TreeMap<String, TommyEnrichedJSON> serviziToPostMap) {
		String method_name = "::PostData(): ";
		
		// Codice Mezzo
		if (codiceMezzo == null) {
			throw new NullPointerException(method_name + "codiceMezzo was nullptr");
		}
		if (codiceMezzo.isBlank()) {
			throw new IllegalArgumentException(method_name + "codiceMezzo was blanck");
		}
		this.codiceMezzo = codiceMezzo;
		
		// jsonServizi
		if (serviziToPostMap == null) {
			throw new NullPointerException(method_name + "serviziToPostMap was nullptr");
		}
		List<String> jsonServiziList = serviziToPostMap.values()
											.stream()
											.map(s -> s.getJsonServizio())
											.collect(Collectors.toList());
		this.jsonServizi = "";
		this.jsonServizi = unifyJSONS((ArrayList<String>) jsonServiziList);
		
		// CodiciServizi
		this.codiciServizi = new ArrayList<String>();
		this.codiciServizi.addAll(serviziToPostMap.keySet());
	}
	
	public String getCodiceMezzo() {
		return codiceMezzo;
	}

	public ArrayList<String> getCodiciServizi() {
		return codiciServizi;
	}

	public String getJsonServizi() {
		return jsonServizi;
	}
	
	public static String unifyJSONS(ArrayList<String> jsonServiziList) {
		String jsonUnified = "";
		if (jsonServiziList == null) { 
			return jsonUnified;
		}
		if (jsonServiziList.isEmpty()) {
			return jsonUnified;
		}
		for (String jsonServizio: jsonServiziList) {
			if (jsonServizio == null) { 
				return "";
			}
			if (jsonServizio.isBlank()) {
				return "";
			}
			if (jsonUnified.isBlank()) { // If first servizio, add leading [
				jsonUnified = "[";
			} else { // If generic servizio, add , and new line to previous servizio
				jsonUnified += ",\n";
			}
			// Remove First { from JSON
			String jsonServizioNoFirstLine = jsonServizio.substring(jsonServizio.indexOf("{") + 1);
			// Remove Up to Second { from JSON
			String jsonServizioNoSecondLine = jsonServizioNoFirstLine.substring(jsonServizioNoFirstLine.indexOf("{") + 1);
			// Remove Last two } from JSON
			String jsonServizioNoLastBracket = jsonServizioNoSecondLine.substring(0, jsonServizioNoSecondLine.lastIndexOf("}"));
			jsonServizioNoLastBracket = jsonServizioNoLastBracket.substring(0, jsonServizioNoLastBracket.lastIndexOf("}"));
			// Remove Front Empty Line from JSON
			String jsonServizioNoEmptyFirstLine = jsonServizioNoLastBracket.substring(jsonServizioNoLastBracket.indexOf('\n')+1);
			// Add Leading { in a new line in jsonUnified
			jsonUnified += "{\n";
			// Add JSON to jsonUnified
			jsonUnified += jsonServizioNoEmptyFirstLine.stripTrailing();
			// Add Ending Bracket to jsonUnified
			jsonUnified += "\n}";
		}
		
		// Add treading ] to json
		jsonUnified += "]";
		// Return
		return jsonUnified;
	}
	
}
