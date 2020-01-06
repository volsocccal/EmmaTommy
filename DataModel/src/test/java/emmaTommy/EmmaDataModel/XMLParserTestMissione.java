package emmaTommy.EmmaDataModel;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import emmaTommy.EmmaDataModel.Missione;
import junit.framework.Test;
import junit.framework.TestSuite;

public class XMLParserTestMissione extends XMLParserTest {

	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public XMLParserTestMissione( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( XMLParserTestMissione.class );
    }

  
	 
	  
	  /** Test Membro Unmarshalling - Missione Generic
	   * @throws JAXBException */
	  public void testMissioneUnmarshallingMissioneGenericH24() throws JAXBException
	  {
		  logger.trace(this.getName());
		  
			String test_file = "test_missione_generic_h24.xml";
			JAXBContext jaxbContext = JAXBContext.newInstance(Missione.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Missione m = (Missione) jaxbUnmarshaller.unmarshal( new File(test_data_folder_missione + "/" + test_file) );
			assertTrue( m.validateObject() );
	  }
	  
	  /** Test Membro Unmarshalling - Missione Generic
	   * @throws JAXBException */
	  public void testMissioneUnmarshallingMissioneGenericGettone() throws JAXBException
	  {
		  logger.trace(this.getName());
		  
			String test_file = "test_missione_generic_get.xml";
			JAXBContext jaxbContext = JAXBContext.newInstance(Missione.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Missione m = (Missione) jaxbUnmarshaller.unmarshal( new File(test_data_folder_missione + "/" + test_file) );
			assertTrue( m.validateObject() );
	  }
	  
	
}
