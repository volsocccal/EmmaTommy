package emmaTommy.TommyDataModel.Factories;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.eclipse.persistence.oxm.MediaType;

import emmaTommy.TommyDataModel.Squadra;
import emmaTommy.TommyDataModel.TommyDataModelEnums;


public class SquadraFactory {
	
	/** Empty Constructor */
	public SquadraFactory() {}
	
	public Squadra buildSquadra() {
		return new Squadra();
	}
	
	public ArrayList<emmaTommy.TommyDataModel.Membro> buildSquadra(emmaTommy.EmmaDataModel.Membri membriMissione) {
		
		// Build new Empty Membri list
		ArrayList<emmaTommy.TommyDataModel.Membro> membriSquadra = new ArrayList<emmaTommy.TommyDataModel.Membro>();
				
		// Check for null or empty Squadra
		if (membriMissione == null) {
			return membriSquadra;
		}
		if (membriMissione.getMembri() == null) {
			return membriSquadra;
		}
		if (membriMissione.getMembri().size() == 0) {
			return membriSquadra;
		}		
		
		// Iterate over every member
		for (emmaTommy.EmmaDataModel.Membro m: membriMissione.getMembri()) {
			if (m != null) {
				if (m.getCognome() != null && m.getNome() != null) {
					if (!m.getCognome().isBlank() && !m.getNome().isBlank()) {
						String tag_idanagrafica = m.getNome() + "_" + m.getCognome();
						String tag_idqualifica = this.qualificaConverter(m.getQualifica());
						emmaTommy.TommyDataModel.Membro newMembro = new emmaTommy.TommyDataModel.Membro(); 
						newMembro.setTagIdAnagrafica(tag_idanagrafica);
						newMembro.setTagIdQualifica(tag_idqualifica);
						membriSquadra.add(newMembro);
					}
				}			
				
			}
		}
		
		// Return squadra		
		return membriSquadra;
		
	}
	
	public Squadra buildSquadraUnmarshallJSON(String json) throws JAXBException {
		
		// Create String Reader
		StringReader jsonReader = new StringReader(json);
						
		// Unmarshaller
		JAXBContext jaxbContext = JAXBContext.newInstance(Squadra.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		jaxbUnmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
	    jaxbUnmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, true);
	    Squadra s = (Squadra) jaxbUnmarshaller.unmarshal(jsonReader);
		if (s == null) {
			throw new NullPointerException("Unmarshalled Squadra Object was nullptr");
		}
		
		// Validate Object
		s.validateObject();
		
		// Return Squadra
		return s;
		
	}
	
	public Squadra buildSquadraUnmarshallXML(String xml) throws JAXBException {
		
		// Create String Reader
		StringReader xmlReader = new StringReader(xml);
				
		// Unmarshaller
		JAXBContext jaxbContext = JAXBContext.newInstance(Squadra.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Squadra s = (Squadra) jaxbUnmarshaller.unmarshal(xmlReader);
		if (s == null) {
			throw new NullPointerException("Unmarshalled Squadra Object was nullptr");
		}
		
		// Validate Object
		s.validateObject();
		
		// Return Squadra
		return s;
		
	}
	
	protected String qualificaConverter(String missioniQualifica) {
		if (missioniQualifica == null) {
			return "NULL Qualifica";
		}
		if (missioniQualifica == "" || missioniQualifica.isEmpty() || missioniQualifica.isBlank()) {
			return "EMPTY Qualifica";
		}
		if (TommyDataModelEnums.qualificheConversion.containsKey(missioniQualifica)) {
			return TommyDataModelEnums.qualificheConversion.get(missioniQualifica);
		} else {
			return "NOT_FOUND: " + missioniQualifica;
		}
	}
	
}
