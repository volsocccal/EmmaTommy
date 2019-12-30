package emmaTommy.DataModel;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import junit.framework.Test;
import junit.framework.TestSuite;

public class XMLParserTestTratta extends XMLParserTest {
	
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public XMLParserTestTratta( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( XMLParserTestTratta.class );
    }

  
	 
	  
	  /** Test Membro Unmarshalling - Tratta
	   * @throws JAXBException */
	  public void testTrattaUnmarshallingTratta() throws JAXBException
	  {
		  logger.trace(this.getName());
		   
	  		String test_file = "test_tratta.xml";
	  		JAXBContext jaxbContext = JAXBContext.newInstance(Tratta.class);
	  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	  		Tratta t = (Tratta) jaxbUnmarshaller.unmarshal( new File(test_data_folder_tratta + "/" + test_file) );
	  		t.toXML_print();
	  		assertTrue( t.validateObject() );
	  }
	   
	  

}
