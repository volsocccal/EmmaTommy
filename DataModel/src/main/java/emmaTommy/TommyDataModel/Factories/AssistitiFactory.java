package emmaTommy.TommyDataModel.Factories;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.eclipse.persistence.oxm.MediaType;

import emmaTommy.TommyDataModel.Assistiti;
import emmaTommy.TommyDataModel.Assistito;
import emmaTommy.TommyDataModel.Squadra;

public class AssistitiFactory {

	/** Empty Constructor */
	public AssistitiFactory() {}
	
	public Assistiti buildAssistiti() {
		return new Assistiti();
	}
	
	@SuppressWarnings("deprecation")
	public ArrayList<emmaTommy.TommyDataModel.Assistito> buildAssistiti(emmaTommy.EmmaDataModel.Pazienti pazienti) {
		
		// Build new Empty Assistiti list
		ArrayList<emmaTommy.TommyDataModel.Assistito> assistitiList = new ArrayList<emmaTommy.TommyDataModel.Assistito>();
				
		// Check for null or empty Squadra
		if (pazienti == null) {
			assistitiList.add(this.buildAssistitoAnonimo());
			return assistitiList;
		}
		if (pazienti.getPazienti() == null) {
			assistitiList.add(this.buildAssistitoAnonimo());
			return assistitiList;
		}
		if (pazienti.getPazienti().size() == 0) {
			assistitiList.add(this.buildAssistitoAnonimo());
			return assistitiList;
		}
		
		// Iterate over every member
		for (emmaTommy.EmmaDataModel.Paziente p: pazienti.getPazienti()) {
			if (p != null) {
				if (p.getNome() != null && p.getCognome() != null) {					
					String codiceFiscale = p.getCognome() + "_" + p.getNome();
					if (p.getDataNascita() != null) {
						int correctYear = p.getDataNascita().getYear() + 1900;
						codiceFiscale += "_" + correctYear;
						int month = p.getDataNascita().getMonth();
						if (month < 10) {
							codiceFiscale += "0" + p.getDataNascita().getMonth();
						} else {
							codiceFiscale += p.getDataNascita().getMonth();
						}
						int day = p.getDataNascita().getDay();
						if (day < 10) {
							codiceFiscale += "0" + p.getDataNascita().getDay();
						} else {
							codiceFiscale += p.getDataNascita().getDay();
						}
					}
					emmaTommy.TommyDataModel.Assistito newAssistito = new emmaTommy.TommyDataModel.Assistito();
					newAssistito.setCodiceFiscale(codiceFiscale);
					newAssistito.setCognome(p.getCognome());
					newAssistito.setNome(p.getNome());
					newAssistito.setSesso(p.getSesso());
					newAssistito.setComuneResidenza(p.getComuneResidenza());
					newAssistito.setDataNascita(p.getDataNascita());
					assistitiList.add(newAssistito);
				} else { // If nome or cognome are null, add the Assistito Anonimo Instead
					assistitiList.add(this.buildAssistitoAnonimo());
				}
			} else { // If patient is null, add the Assistito Anonimo Instead
				assistitiList.add(this.buildAssistitoAnonimo());
			}
		}
		
		// If the Assistiti List is still empty, add the Assistito Anonimo
		if (assistitiList.size() == 0) {
			assistitiList.add(this.buildAssistitoAnonimo());
		}
		
		// Return Assistiti		
		return assistitiList;
		
	}
	
	public Assistiti buildAssistitiUnmarshallJSON(String json) throws JAXBException {
		
		// Create String Reader
		StringReader jsonReader = new StringReader(json);
						
		// Unmarshaller
		JAXBContext jaxbContext = JAXBContext.newInstance(Assistiti.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		jaxbUnmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
	    jaxbUnmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, true);
	    Assistiti ass = (Assistiti) jaxbUnmarshaller.unmarshal(jsonReader);
		if (ass == null) {
			throw new NullPointerException("Unmarshalled Assistiti Object was nullptr");
		}
		
		// Validate Object
		ass.validateObject();
		
		// Return Assistiti
		return ass;
		
	}
	
	public Assistiti buildAssistitiUnmarshallXML(String xml) throws JAXBException {
		
		// Create String Reader
		StringReader xmlReader = new StringReader(xml);
				
		// Unmarshaller
		JAXBContext jaxbContext = JAXBContext.newInstance(Squadra.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Assistiti ass = (Assistiti) jaxbUnmarshaller.unmarshal(xmlReader);
		if (ass == null) {
			throw new NullPointerException("Unmarshalled Assistiti Object was nullptr");
		}
		
		// Validate Object
		ass.validateObject();
		
		// Return Assistiti
		return ass;
		
	}
	
	protected Assistito buildAssistitoAnonimo() {
		String ANONIMO = "ANONIMO";
		Assistito ass = new Assistito();
		ass.setCodiceFiscale(ANONIMO);
		ass.setCognome(ANONIMO);
		ass.setNome(ANONIMO);
		return ass;
	}
	
}
