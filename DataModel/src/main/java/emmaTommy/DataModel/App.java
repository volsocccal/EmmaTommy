package emmaTommy.DataModel;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws JAXBException
    {
    	Membro paperino = new Membro();
    	paperino.setNome("Donald");
    	paperino.setCognome("Duck");
    	paperino.setQualifica("dae");
        System.out.println( "Hello World!" );
        
        JAXBContext jaxbContext = JAXBContext.newInstance(Missioni.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
         
        //We had written this file in marshalling example
        Missioni miss = (Missioni) jaxbUnmarshaller.unmarshal( new File("/media/sapo93/DATI/Dropbox/Volontari/AREU Web Services/data_xml/") );
        
        System.out.println( "Hello World!" );
        
    }
}
