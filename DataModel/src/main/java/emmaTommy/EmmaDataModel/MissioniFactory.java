package emmaTommy.EmmaDataModel;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.eclipse.persistence.oxm.MediaType;

public class MissioniFactory {

	/** Empty Constructor */
	public MissioniFactory() {
	}
	
	public Missioni buildServizio() {
		return new Missioni();
	}
	
	public Missioni buildMissioniUnmarshallJSON(String json) throws JAXBException {
		
		// Create String Reader
		StringReader jsonReader = new StringReader(json);
						
		// Unmarshaller
		JAXBContext jaxbContext = JAXBContext.newInstance(Missioni.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		jaxbUnmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
        jaxbUnmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, true);
		Missioni m = (Missioni) jaxbUnmarshaller.unmarshal(jsonReader);
		if (m == null) {
			throw new NullPointerException("Unmarshalled Missioni Object was nullptr");
		}
		
		// Validate Object
		m.validateObject();
		
		// Return Missioni
		return m;
		
	}
	
	public Missioni buildMissioniUnmarshallXML(String xml) throws JAXBException {
		
		// Create String Reader
		StringReader xmlReader = new StringReader(xml);
				
		// Unmarshaller
		JAXBContext jaxbContext = JAXBContext.newInstance(Missioni.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Missioni m = (Missioni) jaxbUnmarshaller.unmarshal(xmlReader);
		if (m == null) {
			throw new NullPointerException("Unmarshalled Missioni Object was nullptr");
		}
		
		// Validate Object
		m.validateObject();
		
		// Return Missioni
		return m;
		
	}
	
}
