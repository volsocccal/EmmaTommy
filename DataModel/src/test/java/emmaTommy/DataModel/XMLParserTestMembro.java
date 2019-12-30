package emmaTommy.DataModel;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import emmaTommy.DataModel.Membri;
import emmaTommy.DataModel.Membro;
import junit.framework.Test;
import junit.framework.TestSuite;

public class XMLParserTestMembro extends XMLParserTest {
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public XMLParserTestMembro( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( XMLParserTestMembro.class );
    }
    
    
    
    /** Test Membro Unmarshalling - qualifica AUTISTA 
     * @throws JAXBException */
    public void testMembroUnmarshallingAUTISTA() throws JAXBException
    {
    	logger.trace(this.getName());
    	
    	Membro paperino = new Membro("Donald", "Duck", DataModelEnums.AUTISTA);
    	
    	String test_file = "test_membro_autista.xml";
    	JAXBContext jaxbContext = JAXBContext.newInstance(Membro.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Membro m = (Membro) jaxbUnmarshaller.unmarshal( new File(test_data_folder_membro + "/" + test_file) );
        assertTrue( m.getNome().equals(paperino.getNome()) );
        assertTrue( m.getCognome().equals(paperino.getCognome()) );
        assertTrue( m.getQualifica().equals(paperino.getQualifica()) );
        assertTrue( m.getType().equals(paperino.getType()) );
        assertTrue( m.validateObject() );
    }
    
    /** Test Membro Unmarshalling - qualifica CAPOSERVIZIO 
     * @throws JAXBException */
    public void testMembroUnmarshallingCAPOSERVIZIO() throws JAXBException
    {
    	logger.trace(this.getName());
    	
    	Membro paperino = new Membro("Donald", "Duck", DataModelEnums.CAPOSERVIZIO);
    	
    	String test_file = "test_membro_caposervizio.xml";
    	JAXBContext jaxbContext = JAXBContext.newInstance(Membro.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Membro m = (Membro) jaxbUnmarshaller.unmarshal( new File(test_data_folder_membro + "/" + test_file) );
        assertTrue( m.getNome().equals(paperino.getNome()) );
        assertTrue( m.getCognome().equals(paperino.getCognome()) );
        assertTrue( m.getQualifica().equals(paperino.getQualifica()) );
        assertTrue( m.getType().equals(paperino.getType()) );
        assertTrue( m.validateObject() );
    }
    
    /** Test Membro Unmarshalling - qualifica SOCCORRITORE 
     * @throws JAXBException */
    public void testMembroUnmarshallingSOCCORRITORE() throws JAXBException
    {
    	logger.trace(this.getName());
    	
    	Membro paperino = new Membro("Donald", "Duck", DataModelEnums.SOCCORRITORE);    	
    	String test_file = "test_membro_socc.xml";
    	JAXBContext jaxbContext = JAXBContext.newInstance(Membro.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    	Membro m = (Membro) jaxbUnmarshaller.unmarshal( new File(test_data_folder_membro + "/" + test_file) );
    	assertTrue( m.getNome().equals(paperino.getNome()) );
        assertTrue( m.getCognome().equals(paperino.getCognome()) );
        assertTrue( m.getQualifica().equals(paperino.getQualifica()) );
        assertTrue( m.getType().equals(paperino.getType()) );  
        assertTrue( m.validateObject() );
    }
    
    /** Test Membro Unmarshalling - qualifica SOCCORRITORE IN ADDESTRAMENTO
     * @throws JAXBException */
    public void testMembroUnmarshallingSOCCORRITOREADD() throws JAXBException
    {
    	logger.trace(this.getName());
    	
    	Membro paperino = new Membro("Donald", "Duck", DataModelEnums.SOCCORRITORE_ADD);    	
    	String test_file = "test_membro_socc_add.xml";
    	JAXBContext jaxbContext = JAXBContext.newInstance(Membro.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    	Membro m = (Membro) jaxbUnmarshaller.unmarshal( new File(test_data_folder_membro + "/" + test_file) );
    	assertTrue( m.getNome().equals(paperino.getNome()) );
        assertTrue( m.getCognome().equals(paperino.getCognome()) );
        assertTrue( m.getQualifica().equals(paperino.getQualifica()) );
        assertTrue( m.getType().equals(paperino.getType()) );  
        assertTrue( m.validateObject() );
    }        
    
    
    
    /** Test Membro Unmarshalling - Null Name
     * @throws JAXBException */
    public void testMembroUnmarshallingNullNome() throws JAXBException
    {
    	logger.trace(this.getName());
    	
    	String test_file = "test_membro_null_nome.xml";
    	JAXBContext jaxbContext = JAXBContext.newInstance(Membro.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    	Membro m = (Membro) jaxbUnmarshaller.unmarshal( new File(test_data_folder_membro + "/" + test_file) );
    	assertFalse( m.validateObject() );
    }
    
    /** Test Membro Unmarshalling - Null Cognome
    * @throws JAXBException */
   public void testMembroUnmarshallingNullCognome() throws JAXBException
   {
	   logger.trace(this.getName());
	   
	   	String test_file = "test_membro_null_cognome.xml";
	   	JAXBContext jaxbContext = JAXBContext.newInstance(Membro.class);
	   	Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();	   
		Membro m = (Membro) jaxbUnmarshaller.unmarshal( new File(test_data_folder_membro + "/" + test_file) );
		assertFalse( m.validateObject() );       
   }
   
   /** Test Membro Unmarshalling - Null Qualifica
    * @throws JAXBException */
   public void testMembroUnmarshallingNullQualifica() throws JAXBException
   {
	   logger.trace(this.getName());
	   
	   String test_file = "test_membro_null_qualifica.xml";
	   JAXBContext jaxbContext = JAXBContext.newInstance(Membro.class);	
	   Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	   Membro m = (Membro) jaxbUnmarshaller.unmarshal( new File(test_data_folder_membro + "/" + test_file) );
	   assertFalse( m.validateObject() ); 
   }
   

   
   /** Test Membro Unmarshalling - Null Name
    * @throws JAXBException */
   public void testMembroUnmarshallingMissingNome() throws JAXBException
   {
	   logger.trace(this.getName());
	   
		String test_file = "test_membro_missing_nome.xml";
		JAXBContext jaxbContext = JAXBContext.newInstance(Membro.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Membro m = (Membro) jaxbUnmarshaller.unmarshal( new File(test_data_folder_membro + "/" + test_file) );
		assertFalse( m.validateObject() ); 
   }
   
   /** Test Membro Unmarshalling - Missing Cognome
   * @throws JAXBException */
  public void testMembroUnmarshallingNullMissingCognome() throws JAXBException
  {	   
	  logger.trace(this.getName());
	  
  		String test_file = "test_membro_missing_cognome.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Membro.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Membro m = (Membro) jaxbUnmarshaller.unmarshal( new File(test_data_folder_membro + "/" + test_file) );
		assertFalse( m.validateObject() );
  }
  
  /** Test Membro Unmarshalling - Missing Qualifica
   * @throws JAXBException */
  public void testMembroUnmarshallingMissingQualifica() throws JAXBException
  {
	  logger.trace(this.getName());
	   
	   String test_file = "test_membro_missing_qualifica.xml";
	   JAXBContext jaxbContext = JAXBContext.newInstance(Membro.class);
	   Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	   Membro m = (Membro) jaxbUnmarshaller.unmarshal( new File(test_data_folder_membro + "/" + test_file) );
	   assertFalse( m.validateObject() );
  }
  
  /** Test Membro Unmarshalling - Wrong Qualifica
   * @throws JAXBException */
  public void testMembroUnmarshallingWrongQualifica() throws JAXBException
  {
	  logger.trace(this.getName());
	  	   
	   String test_file = "test_membro_wrong_qualifica.xml";
	   JAXBContext jaxbContext = JAXBContext.newInstance(Membro.class);
	   Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	   Membro m = (Membro) jaxbUnmarshaller.unmarshal( new File(test_data_folder_membro + "/" + test_file) );
	   assertFalse( m.validateObject() );
  }
  
  

  /** Test Membro Unmarshalling - Membri List
   * @throws JAXBException */
  public void testMembriUnmarshallingList() throws JAXBException
  {
	  	logger.trace(this.getName());
	  
	  	Membro paperino = new Membro("DONALD", "AUTISTA", DataModelEnums.AUTISTA);   
		Membro topolino = new Membro("MICKEY", "MOUSE", DataModelEnums.SOCCORRITORE);   
		Membro pippo = new Membro("GOOFY", "GOOF", DataModelEnums.CAPOSERVIZIO);   
		Membro paperone = new Membro("SCRUDGE", "MCDUCK", DataModelEnums.SOCCORRITORE_ADD);   
		
		String test_file = "test_membri.xml";
		JAXBContext jaxbContext = JAXBContext.newInstance(Membri.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Membri membri = (Membri) jaxbUnmarshaller.unmarshal( new File(test_data_folder_membro + "/" + test_file) );
		membri.getMembri().get(0).equals(paperino);
		membri.getMembri().get(1).equals(topolino);
		membri.getMembri().get(2).equals(pippo);
		membri.getMembri().get(3).equals(paperone);
		assertTrue( membri.validateObject() );
  }
      
  /** Test Membro Unmarshalling - Membri Empty List
   * @throws JAXBException */
  public void testMembriUnmarshallingEmptyList() throws JAXBException
  {
	  	logger.trace(this.getName());
	  	
  		String test_file = "test_membri_empty_array.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Membri.class);
	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	  	Membri membri = (Membri) jaxbUnmarshaller.unmarshal( new File(test_data_folder_membro + "/" + test_file) );
	  	assertFalse( membri.validateObject() );	
  }
  
  
}
