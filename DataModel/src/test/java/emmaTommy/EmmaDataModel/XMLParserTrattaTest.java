package emmaTommy.EmmaDataModel;

import java.io.File;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import emmaTommy.EmmaDataModel.Tratta;
import emmaTommy.EmmaDataModel.Tratte;
import junit.framework.Test;
import junit.framework.TestSuite;

public class XMLParserTrattaTest extends XMLParserTest {
	
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public XMLParserTrattaTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( XMLParserTrattaTest.class );
    }

  
	 
	  
	  /** Test Membro Unmarshalling - Tratta Generic
	   * @throws JAXBException */
	  public void testTrattaUnmarshallingTrattaGeneric() throws JAXBException
	  {
		  logger.trace(this.getName());
		  
		  @SuppressWarnings("deprecation")
		  Tratta tratta = new Tratta (1, new Date(119, 9, 7, 8, 01, 05), new Date(119, 9, 7, 7, 16, 44), "H LECCO");
		  
			String test_file = "test_tratta_generic.xml";
			JAXBContext jaxbContext = JAXBContext.newInstance(Tratta.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Tratta t = (Tratta) jaxbUnmarshaller.unmarshal( new File(test_data_folder_tratta + "/" + test_file) );
			assertTrue( t.getId() == tratta.getId());
			assertTrue (t.getDataPartenza().compareTo(tratta.getDataPartenza()) == 0);
			assertTrue (t.getDataArrivo().compareTo(tratta.getDataArrivo()) == 0);
			assertTrue (t.getDestinazione().compareTo(tratta.getDestinazione()) == 0);
			assertTrue( t.validateObject() );
	  }
	  
	  
	  /** Test Membro Unmarshalling - Tratta Id Empty
	   * @throws JAXBException */
	  public void testTrattaUnmarshallingTrattaIdEmpty() throws JAXBException
	  {
		  logger.trace(this.getName());
		  
		  String test_file = "test_tratta_id_empty.xml";
		  JAXBContext jaxbContext = JAXBContext.newInstance(Tratta.class);
		  Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		  Tratta t = (Tratta) jaxbUnmarshaller.unmarshal( new File(test_data_folder_tratta + "/" + test_file) );
		  assertTrue( t.getId() == 0);
		  assertFalse( t.validateObject() );
	  }
	  
	  /** Test Membro Unmarshalling - Tratta Id Missing
	   * @throws JAXBException */
	  public void testTrattaUnmarshallingTrattaIdMissing() throws JAXBException
	  {
		  logger.trace(this.getName());
		  
		  String test_file = "test_tratta_id_missing.xml";
		  JAXBContext jaxbContext = JAXBContext.newInstance(Tratta.class);
		  Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		  Tratta t = (Tratta) jaxbUnmarshaller.unmarshal( new File(test_data_folder_tratta + "/" + test_file) );
		  assertTrue( t.getId() == 0);
		  assertFalse( t.validateObject() );
	  }
	  
	  /** Test Membro Unmarshalling - Tratta Id Null
	   * @throws JAXBException */
	  public void testTrattaUnmarshallingTrattaIdNull() throws JAXBException
	  {
		  logger.trace(this.getName());
		  
		  String test_file = "test_tratta_id_null.xml";
		  JAXBContext jaxbContext = JAXBContext.newInstance(Tratta.class);
		  Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		  Tratta t = (Tratta) jaxbUnmarshaller.unmarshal( new File(test_data_folder_tratta + "/" + test_file) );
		  assertTrue( t.getId() == 0);
		  assertFalse( t.validateObject() );
	  }
	  
	  
	  /** Test Membro Unmarshalling - Tratta Data Partenza Empty
	   * @throws JAXBException */
	  public void testTrattaUnmarshallingTrattaDataPartenzaEmpty() throws JAXBException
	  {
		  logger.trace(this.getName());
		  
		  String test_file = "test_tratta_data_partenza_empty.xml";
		  JAXBContext jaxbContext = JAXBContext.newInstance(Tratta.class);
		  Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		  Tratta t = (Tratta) jaxbUnmarshaller.unmarshal( new File(test_data_folder_tratta + "/" + test_file) );
		  assertTrue( t.validateObject() );
	  }
	  
	  /** Test Membro Unmarshalling - Tratta Data Partenza Missing
	   * @throws JAXBException */
	  public void testTrattaUnmarshallingTrattaDataPartenzaMissing() throws JAXBException
	  {
		  logger.trace(this.getName());
		  
		  String test_file = "test_tratta_data_partenza_missing.xml";
		  JAXBContext jaxbContext = JAXBContext.newInstance(Tratta.class);
		  Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		  Tratta t = (Tratta) jaxbUnmarshaller.unmarshal( new File(test_data_folder_tratta + "/" + test_file) );
		  assertTrue( t.validateObject() );
	  }
	  
	  /** Test Membro Unmarshalling - Tratta Data Partenza Null
	   * @throws JAXBException */
	  public void testTrattaUnmarshallingTrattaDataPartenzaNull() throws JAXBException
	  {
		  logger.trace(this.getName());
		  
		  String test_file = "test_tratta_data_partenza_null.xml";
		  JAXBContext jaxbContext = JAXBContext.newInstance(Tratta.class);
		  Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		  Tratta t = (Tratta) jaxbUnmarshaller.unmarshal( new File(test_data_folder_tratta + "/" + test_file) );
		  assertTrue( t.validateObject() );
	  }
	  
	  
	  /** Test Membro Unmarshalling - Tratta Data Arrivo Empty
	   * @throws JAXBException */
	  public void testTrattaUnmarshallingTrattaDataArrivoEmpty() throws JAXBException
	  {
		  logger.trace(this.getName());
		  
		  String test_file = "test_tratta_data_arrivo_empty.xml";
		  JAXBContext jaxbContext = JAXBContext.newInstance(Tratta.class);
		  Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		  Tratta t = (Tratta) jaxbUnmarshaller.unmarshal( new File(test_data_folder_tratta + "/" + test_file) );
		  assertFalse( t.validateObject() );
	  }
	  
	  /** Test Membro Unmarshalling - Tratta Data Arrivo Missing
	   * @throws JAXBException */
	  public void testTrattaUnmarshallingTrattaDataArrivoMissing() throws JAXBException
	  {
		  logger.trace(this.getName());
		  
		  String test_file = "test_tratta_data_arrivo_missing.xml";
		  JAXBContext jaxbContext = JAXBContext.newInstance(Tratta.class);
		  Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		  Tratta t = (Tratta) jaxbUnmarshaller.unmarshal( new File(test_data_folder_tratta + "/" + test_file) );
		  assertFalse( t.validateObject() );
	  }
	  
	  /** Test Membro Unmarshalling - Tratta Data Arrivo Null
	   * @throws JAXBException */
	  public void testTrattaUnmarshallingTrattaDataArrivoNull() throws JAXBException
	  {
		  logger.trace(this.getName());
		  
		  String test_file = "test_tratta_data_arrivo_null.xml";
		  JAXBContext jaxbContext = JAXBContext.newInstance(Tratta.class);
		  Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		  Tratta t = (Tratta) jaxbUnmarshaller.unmarshal( new File(test_data_folder_tratta + "/" + test_file) );
		  assertFalse( t.validateObject() );
	  }
	  
	  
	  /** Test Membro Unmarshalling - Tratta Data Partenza Before Arrivo
	   * @throws JAXBException */
	  public void testTrattaUnmarshallingTrattaDataPartenzaBeforeArrivo() throws JAXBException
	  {
		  logger.trace(this.getName());
		  
		  String test_file = "test_tratta_data_partenza_before_arrivo.xml";
		  JAXBContext jaxbContext = JAXBContext.newInstance(Tratta.class);
		  Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		  Tratta t = (Tratta) jaxbUnmarshaller.unmarshal( new File(test_data_folder_tratta + "/" + test_file) );
		  assertFalse( t.validateObject() );
	  }
	  
   
	  

	  /** Test Membro Unmarshalling - Tratte List
	   * @throws JAXBException */
	  public void testTratteUnmarshallingList() throws JAXBException
	  {
		  	logger.trace(this.getName());
		  
		  	@SuppressWarnings("deprecation")
			Tratta tratta_1 = new Tratta (1, new Date(119, 9, 7, 7, 16, 44), new Date(119, 9, 7, 8, 01, 05), "");
		  	@SuppressWarnings("deprecation")
			Tratta tratta_2 = new Tratta (2, new Date(119, 9, 7, 8, 34, 2), new Date(119, 9, 7, 8, 49, 1), "H LECCO");
			
			String test_file = "test_tratte.xml";
			JAXBContext jaxbContext = JAXBContext.newInstance(Tratte.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Tratte tratte = (Tratte) jaxbUnmarshaller.unmarshal( new File(test_data_folder_tratta + "/" + test_file) );
			tratte.getTratte().get(0).equals(tratta_1);
			tratte.getTratte().get(1).equals(tratta_2);
			assertTrue( tratte.validateObject() );
	  }
	  
	  /** Test Membro Unmarshalling - Tratte List Repeated Id
	   * @throws JAXBException */
	  public void testTratteUnmarshallingListRepeatedId() throws JAXBException
	  {
		  	logger.trace(this.getName());
		  
		  	String test_file = "test_tratte_repeated_id.xml";
			JAXBContext jaxbContext = JAXBContext.newInstance(Tratte.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Tratte tratte = (Tratte) jaxbUnmarshaller.unmarshal( new File(test_data_folder_tratta + "/" + test_file) );
			assertFalse( tratte.validateObject() );
	  }
	      
	  /** Test Membro Unmarshalling - Tratte Empty List
	   * @throws JAXBException */
	  public void testMembriUnmarshallingEmptyList() throws JAXBException
	  {
		  	logger.trace(this.getName());
		  	
	  		String test_file = "test_tratte_empty_array.xml";
	  		JAXBContext jaxbContext = JAXBContext.newInstance( Tratte.class);
		    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		    Tratte tratte = ( Tratte) jaxbUnmarshaller.unmarshal( new File(test_data_folder_tratta + "/" + test_file) );
		  	assertFalse( tratte.validateObject() );	
	  }
	  
	  

}
