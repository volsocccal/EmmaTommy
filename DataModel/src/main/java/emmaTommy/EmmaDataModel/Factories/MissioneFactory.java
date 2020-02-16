package emmaTommy.EmmaDataModel.Factories;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.eclipse.persistence.oxm.MediaType;

import emmaTommy.EmmaDataModel.Missione;

public class MissioneFactory {

	/** Empty Constructor */
	public MissioneFactory() {
	}
	
	public Missione buildMissione() {
		return new Missione();
	}
	
	public Missione buildMissioneUnmarshallJSON(String json) throws JAXBException {
		
		// Create String Reader
		StringReader jsonReader = new StringReader(json);
						
		// Unmarshaller
		JAXBContext jaxbContext = JAXBContext.newInstance(Missione.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		jaxbUnmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
        jaxbUnmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, true);
		Missione m = (Missione) jaxbUnmarshaller.unmarshal(jsonReader);
		if (m == null) {
			throw new NullPointerException("Unmarshalled Missione Object was nullptr");
		}
		
		// Validate Object
		m.validateObject();
		
		// Return Missione
		return m;
		
	}
	
	public Missione buildMissioneUnmarshallXML(String xml) throws JAXBException {
		
		// Create String Reader
		StringReader xmlReader = new StringReader(xml);
				
		// Unmarshaller
		JAXBContext jaxbContext = JAXBContext.newInstance(Missione.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Missione m = (Missione) jaxbUnmarshaller.unmarshal(xmlReader);
		if (m == null) {
			throw new NullPointerException("Unmarshalled Missione Object was nullptr");
		}
		
		// Validate Object
		m.validateObject();
		
		// Return Missione
		return m;
		
	}
	
}
