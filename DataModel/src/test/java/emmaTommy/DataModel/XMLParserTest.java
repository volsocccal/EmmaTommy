package emmaTommy.DataModel;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import emmaTommy.DataModel.Membri;
import emmaTommy.DataModel.Membro;
import emmaTommy.DataModel.Tratta;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class XMLParserTest extends TestCase
{
	String test_folder = "../data_xml_test/";
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public XMLParserTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( XMLParserTest.class );
    }
    
    /** Test Membro Unmarshalling - qualifica DAE 
     * @throws JAXBException */
    public void testMembroUnmarshallingDAE() throws JAXBException
    {
    	Membro paperino = new Membro("Donald", "Duck", Membro.DAE);    	
    	String test_file = "test_membro_dae.xml";
    	JAXBContext jaxbContext = JAXBContext.newInstance(Membro.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    	Membro m = (Membro) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
    	assertTrue(m.getOkStatus());
    	assertTrue( m.getNome().equals(paperino.getNome()) );
        assertTrue( m.getCognome().equals(paperino.getCognome()) );
        assertTrue( m.getQualifica().equals(paperino.getQualifica()) );
        assertTrue( m.getType().equals(paperino.getType()) );
        
    }
        
    /** Test Membro Unmarshalling - qualifica CAPOSERVIZIO 
     * @throws JAXBException */
    public void testMembroUnmarshallingCAPOSERVIZIO() throws JAXBException
    {
    	Membro paperino = new Membro("Donald", "Duck", Membro.CAPOSERVIZIO);
    	
    	String test_file = "test_membro_caposervizio.xml";
    	JAXBContext jaxbContext = JAXBContext.newInstance(Membro.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Membro m = (Membro) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
        assertTrue(m.getOkStatus());
        assertTrue( m.getNome().equals(paperino.getNome()) );
        assertTrue( m.getCognome().equals(paperino.getCognome()) );
        assertTrue( m.getQualifica().equals(paperino.getQualifica()) );
        assertTrue( m.getType().equals(paperino.getType()) );
    }
    
    /** Test Membro Unmarshalling - qualifica AUTISTA 
     * @throws JAXBException */
    public void testMembroUnmarshallingAUTISTA() throws JAXBException
    {
    	Membro paperino = new Membro("Donald", "Duck", Membro.AUTISTA);
    	
    	String test_file = "test_membro_autista.xml";
    	JAXBContext jaxbContext = JAXBContext.newInstance(Membro.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Membro m = (Membro) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
        assertTrue(m.getOkStatus());
        assertTrue( m.getNome().equals(paperino.getNome()) );
        assertTrue( m.getCognome().equals(paperino.getCognome()) );
        assertTrue( m.getQualifica().equals(paperino.getQualifica()) );
        assertTrue( m.getType().equals(paperino.getType()) );
    }
        
    
    /** Test Membro Unmarshalling - Null Name
     * @throws JAXBException */
    public void testMembroUnmarshallingNullNome() throws JAXBException
    {
    	Membro paperino = new Membro("", "Duck", Membro.AUTISTA);
    	
    	String test_file = "test_membro_null_nome.xml";
    	JAXBContext jaxbContext = JAXBContext.newInstance(Membro.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Membro m = (Membro) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
        assertTrue(m.getOkStatus());
        assertTrue( m.getNome().equals(paperino.getNome()) );
        assertTrue( m.getCognome().equals(paperino.getCognome()) );
        assertTrue( m.getQualifica().equals(paperino.getQualifica()) );
        assertTrue( m.getType().equals(paperino.getType()) );  
    }
    
    /** Test Membro Unmarshalling - Null Cognome
    * @throws JAXBException */
   public void testMembroUnmarshallingNullCognome() throws JAXBException
   {
	   Membro paperino = new Membro("Donald", "", Membro.AUTISTA);
	   
   		String test_file = "test_membro_null_cognome.xml";
   		JAXBContext jaxbContext = JAXBContext.newInstance(Membro.class);
   		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
   		Membro m = (Membro) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
   		assertTrue(m.getOkStatus());
        assertTrue( m.getNome().equals(paperino.getNome()) );
        assertTrue( m.getCognome().equals(paperino.getCognome()) );
        assertTrue( m.getQualifica().equals(paperino.getQualifica()) );
        assertTrue( m.getType().equals(paperino.getType()) );     
   }
   
   /** Test Membro Unmarshalling - Null Qualifica
    * @throws JAXBException */
   public void testMembroUnmarshallingNullQualifica() throws JAXBException
   {
	   Membro paperino = new Membro("Donald", "Duck", "");
	   
   		String test_file = "test_membro_null_qualifica.xml";
   		JAXBContext jaxbContext = JAXBContext.newInstance(Membro.class);
   		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
   		Membro m = (Membro) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
        assertTrue(m.getOkStatus());
        assertTrue( m.getNome().equals(paperino.getNome()) );
        assertTrue( m.getCognome().equals(paperino.getCognome()) );
        assertTrue( m.getQualifica().equals(paperino.getQualifica()) );
        assertTrue( m.getType().equals(paperino.getType()) );
   }
   

   /** Test Membro Unmarshalling - Null Name
    * @throws JAXBException */
   public void testMembroUnmarshallingMissingNome() throws JAXBException
   {
   	Membro paperino = new Membro(null, "Duck", Membro.AUTISTA);
   	
   	String test_file = "test_membro_missing_nome.xml";
   	JAXBContext jaxbContext = JAXBContext.newInstance(Membro.class);
       Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
       Membro m = (Membro) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
       assertTrue(m.getOkStatus());
       assertTrue( m.getNome() == null);
       assertTrue( m.getCognome().equals(paperino.getCognome()) );
       assertTrue( m.getQualifica().equals(paperino.getQualifica()) );
       assertTrue( m.getType().equals(paperino.getType()) );  
   }
   
   /** Test Membro Unmarshalling - Missing Cognome
   * @throws JAXBException */
  public void testMembroUnmarshallingNullMissingCognome() throws JAXBException
  {
	   Membro paperino = new Membro("Donald", null, Membro.AUTISTA);
	   
  		String test_file = "test_membro_missing_cognome.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Membro.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Membro m = (Membro) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		assertTrue(m.getOkStatus());
       assertTrue( m.getNome().equals(paperino.getNome()) );
       assertTrue( m.getCognome() == null );
       assertTrue( m.getQualifica().equals(paperino.getQualifica()) );
       assertTrue( m.getType().equals(paperino.getType()) );     
  }
  
  /** Test Membro Unmarshalling - Missing Qualifica
   * @throws JAXBException */
  public void testMembroUnmarshallingMissingQualifica() throws JAXBException
  {
	   Membro paperino = new Membro("Donald", "Duck", null);
	   
  		String test_file = "test_membro_missing_qualifica.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Membro.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Membro m = (Membro) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
       assertTrue(m.getOkStatus());
       assertTrue( m.getNome().equals(paperino.getNome()) );
       assertTrue( m.getCognome().equals(paperino.getCognome()) );
       assertTrue( m.getQualifica() == null );
       assertTrue( m.getType().equals(paperino.getType()) );
  }
  

  /** Test Membro Unmarshalling - Membri List
   * @throws JAXBException */
  public void testMembriUnmarshallingList() throws JAXBException
  {
  	Membro paperino = new Membro("DONALD", "AUTISTA", Membro.AUTISTA);   
  	Membro topolino = new Membro("MICKEY", "MOUSE", Membro.SOCCORRITORE);   
  	Membro pippo = new Membro("GOOFY", "GOOF", Membro.CAPOSERVIZIO);   
  	Membro paperone = new Membro("SCRUDGE", "MCDUCK", Membro.SOCCORRITORE);   
  	
  	String test_file = "test_membri.xml";
  	JAXBContext jaxbContext = JAXBContext.newInstance(Membri.class);
    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  	Membri membri = (Membri) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  	membri.getMembri().get(0).equals(paperino);
  	membri.getMembri().get(1).equals(topolino);
  	membri.getMembri().get(2).equals(pippo);
  	membri.getMembri().get(3).equals(paperone);
  }
      
  /** Test Membro Unmarshalling - Membri Empty List
   * @throws JAXBException */
  public void testMembriUnmarshallingEmptyList() throws JAXBException
  {
  	String test_file = "test_membri_empty_array.xml";
  	JAXBContext jaxbContext = JAXBContext.newInstance(Membri.class);
    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  	Membri membri = (Membri) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  	assertTrue(membri.getMembri().size() == 0);  	
  }
  
  /** Test Membro Unmarshalling - Missing Qualifica
   * @throws JAXBException */
  public void testMembroUnmarshallingTratta() throws JAXBException
  {
	   //Tratta paperino = new Tratta(1, "Duck", null);
	   
  		String test_file = "test_tratta.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Tratta.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Tratta t = (Tratta) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		t.toXML_print();
  		t.toJSON_print();
  }
   
  
}
