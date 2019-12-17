package emmaTommy.DataModel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.oxm.MediaType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class toDataFormatClass {
	
	public toDataFormatClass() {}
	
	/** Marshal Class to JSON */
	public String toJSON() throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(this.getClass());
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        jaxbMarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
        jaxbMarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(this, sw);
        return sw.toString();
	}
	/** Print Class Marshalled as JSON */
	public void toJSON_print() throws JAXBException {
    	System.out.println(this.toJSON());
    }
	/** Write to File Class Marshalled as JSON */
    public void toJSON_file(String filePath) throws IOException, JAXBException {
    	FileOutputStream outputStream = new FileOutputStream(filePath);
        outputStream.write(this.toJSON().getBytes());     
        outputStream.close();
    }

    /** Marshal Class to XML */
	public String toXML() throws JAXBException {
    	JAXBContext jaxbContext = JAXBContext.newInstance(this.getClass());
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        jaxbMarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_XML);
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(this, sw);
        return sw.toString();
    }
	/** Print Class Marshalled as XML */
    public void toXML_print() throws JAXBException {
    	System.out.println(this.toXML());
    }
    /** Write to File Class Marshalled as XML */
    public void toXML_file(String filePath) throws IOException, JAXBException {
    	FileOutputStream outputStream = new FileOutputStream(filePath);
        outputStream.write(this.toXML().getBytes());     
        outputStream.close();
    }
	
}
