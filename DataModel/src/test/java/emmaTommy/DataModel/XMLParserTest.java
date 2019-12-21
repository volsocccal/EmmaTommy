package emmaTommy.DataModel;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.logging.log4j.LogManager;

import emmaTommy.DataModel.Membri;
import emmaTommy.DataModel.Membro;
import emmaTommy.DataModel.Tratta;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class XMLParserTest extends TestCase
{
	
	static org.apache.logging.log4j.Logger logger = LogManager.getLogger("dataModel");
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
    
    
    
    /** Test Membro Unmarshalling - qualifica AUTISTA 
     * @throws JAXBException */
    public void testMembroUnmarshallingAUTISTA() throws JAXBException
    {
    	logger.trace(this.getName());
    	
    	Membro paperino = new Membro("Donald", "Duck", DataModelEnums.AUTISTA);
    	
    	String test_file = "test_membro_autista.xml";
    	JAXBContext jaxbContext = JAXBContext.newInstance(Membro.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Membro m = (Membro) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
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
        Membro m = (Membro) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
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
    	Membro m = (Membro) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
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
    	Membro m = (Membro) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
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
    	Membro m = (Membro) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
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
		Membro m = (Membro) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
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
	   Membro m = (Membro) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
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
		Membro m = (Membro) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
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
		Membro m = (Membro) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
		assertFalse( m.validateObject() );
  }
  
  /** Test Membro Unmarshalling - Missing Qualifica
   * @throws JAXBException */
  public void testMembroUnmarshallingMissingQualifica() throws JAXBException
  {
	  logger.trace(this.getName());
	  
	   Membro paperino = new Membro("Donald", "Duck", null);
	   
	   String test_file = "test_membro_missing_qualifica.xml";
	   JAXBContext jaxbContext = JAXBContext.newInstance(Membro.class);
	   Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	   Membro m = (Membro) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
	   assertFalse( m.validateObject() );
  }
  
  /** Test Membro Unmarshalling - Wrond Qualifica
   * @throws JAXBException */
  public void testMembroUnmarshallingWrongQualifica() throws JAXBException
  {
	  logger.trace(this.getName());
	  
	   Membro paperino = new Membro("Donald", "Duck", null);
	   
	   String test_file = "test_membro_wrong_qualifica.xml";
	   JAXBContext jaxbContext = JAXBContext.newInstance(Membro.class);
	   Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	   Membro m = (Membro) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
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
		Membri membri = (Membri) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
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
	  	Membri membri = (Membri) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
	  	assertFalse( membri.validateObject() );	
  }
  
  /** Test Membro Unmarshalling - Paziente Generic Donald Duck
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteGenericDonaldDuck() throws JAXBException
  {
	  logger.trace(this.getName());
	  
	   Paziente paperino = new Paziente("Donald", "Duck", DataModelEnums.MALE_GENDER,
			    (new GregorianCalendar(1980, Calendar.FEBRUARY, 11).getTime()), 39, 
				DataModelEnums.PEDIATRIC_NO,
				DataModelEnums.OUTCOME_TRASPORTO_REGOLARE,
				DataModelEnums.CODES_VERDE,
				60, DataModelEnums.FR_NORMALE,
				DataModelEnums.COSCIENCE_ALERT,
				"Paperopoli");
	   
  		String test_file = "test_paziente_generic_donald_duck.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.codicePazienteArrivo.compareTo(paperino.codicePazienteArrivo) == 0);
  		assertTrue (p.cognome.compareTo(paperino.cognome) == 0);
  		assertTrue (p.comuneResidenza.compareTo(paperino.comuneResidenza) == 0);
  		assertTrue (p.coscienza.compareTo(paperino.coscienza) == 0);
  		assertTrue (p.dataNascita.compareTo(paperino.dataNascita) == 0);
  		assertTrue (p.eta == paperino.eta);  		
  		assertTrue (p.FC == paperino.FC);
  		assertTrue (p.FR == paperino.FR);
  		assertTrue (p.esitoTrasporto.compareTo(paperino.esitoTrasporto) == 0);
  		assertTrue (p.nome.compareTo(paperino.nome) == 0);
  		assertTrue (p.pediatrico.compareTo(paperino.pediatrico) == 0);
  		assertTrue (p.sesso.compareTo(paperino.sesso) == 0);
  		assertTrue (p.type.compareTo(paperino.type) == 0);
  		
  }
  
  /** Test Membro Unmarshalling - Paziente Generic Minnie Mouse
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteGenericMinnieMouse() throws JAXBException
  {
	  logger.trace(this.getName());
	  
	   Paziente minnie = new Paziente("Minnie", "Mouse", DataModelEnums.FEMALE_GENDER,
			    (new GregorianCalendar(1950, Calendar.JUNE, 3).getTime()), 69, 
				DataModelEnums.PEDIATRIC_NO,
				DataModelEnums.OUTCOME_TRASPORTO_RIFIUTO_TRASPORTO,
				DataModelEnums.CODES_GIALLO,
				100, DataModelEnums.FR_DIFFICOLTOSO,
				DataModelEnums.COSCIENCE_VERBAL,
				"Topolinia");
	   
  		String test_file = "test_paziente_generic_minnie_mouse.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.codicePazienteArrivo.compareTo(minnie.codicePazienteArrivo) == 0);
  		assertTrue (p.cognome.compareTo(minnie.cognome) == 0);
  		assertTrue (p.comuneResidenza.compareTo(minnie.comuneResidenza) == 0);
  		assertTrue (p.coscienza.compareTo(minnie.coscienza) == 0);
  		assertTrue (p.dataNascita.compareTo(minnie.dataNascita) == 0);
  		assertTrue (p.eta == minnie.eta);  		
  		assertTrue (p.FC == minnie.FC);
  		assertTrue (p.FR == minnie.FR);
  		assertTrue (p.esitoTrasporto.compareTo(minnie.esitoTrasporto) == 0);
  		assertTrue (p.nome.compareTo(minnie.nome) == 0);
  		assertTrue (p.pediatrico.compareTo(minnie.pediatrico) == 0);
  		assertTrue (p.sesso.compareTo(minnie.sesso) == 0);
  		assertTrue (p.type.compareTo(minnie.type) == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente Generic Minimal Patient (Only Transport Outcome)
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteGenericMinimal() throws JAXBException
  {
	  logger.trace(this.getName());
	  
	   Paziente minimal = new Paziente(DataModelEnums.OUTCOME_TRASPORTO_REGOLARE);
	   
  		String test_file = "test_paziente_generic_minimal.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.esitoTrasporto.compareTo(minimal.esitoTrasporto) == 0);
  }
  
  
  
  /** Test Membro Unmarshalling - Paziente Codice Rosso
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteCodiceRosso() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_codice_rosso.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.codicePazienteArrivo.compareTo(DataModelEnums.CODES_ROSSO) == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente Codice Giallo
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteCodiceGiallo() throws JAXBException
  {
	  logger.trace(this.getName());
	  	   
  		String test_file = "test_paziente_codice_giallo.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.codicePazienteArrivo.compareTo(DataModelEnums.CODES_GIALLO) == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente Codice Verde
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteCodiceVerde() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_codice_verde.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.codicePazienteArrivo.compareTo(DataModelEnums.CODES_VERDE) == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente Codice Blu
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteCodiceBlu() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_codice_blu.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertFalse( p.validateObject() );
  		assertTrue (p.codicePazienteArrivo.compareTo("B") == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente Codice Empty
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteCodiceEmpty() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_codice_empty.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.codicePazienteArrivo == "");
  }
  
  /** Test Membro Unmarshalling - Paziente Codice Missing
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteCodiceMissing() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_codice_missing.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.codicePazienteArrivo == null);
  }
  
  /** Test Membro Unmarshalling - Paziente Codice Null
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteCodiceNull() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_codice_null.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.codicePazienteArrivo == "");
  }
  
  
  
  /** Test Membro Unmarshalling - Paziente Cognome Empty
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteCognomeEmpty() throws JAXBException
  {
	  logger.trace(this.getName());
	    
  		String test_file = "test_paziente_cognome_empty.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.cognome == "");
  }
  
  /** Test Membro Unmarshalling - Paziente Cognome Missing
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteCognomeMissing() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_cognome_missing.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.cognome == null);
  }
  
  /** Test Membro Unmarshalling - Paziente Cognome Null
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteCognomeNull() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_cognome_null.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.cognome == "");
  }
      
  
  
  /** Test Membro Unmarshalling - Paziente Comune Empty
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteComuneEmpty() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_comune_empty.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.comuneResidenza == "");
  }
  
  /** Test Membro Unmarshalling - Paziente Comune Missing
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteComuneMissing() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_comune_missing.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.comuneResidenza == null);
  }
  
  /** Test Membro Unmarshalling - Paziente Comune Null
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteComuneNull() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_comune_null.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.comuneResidenza == "");
  }
  
    

  /** Test Membro Unmarshalling - Paziente Coscienza NOTSET
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteCoscienzaNOTSET() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_coscienza_NOTSET.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.coscienza.compareTo(DataModelEnums.COSCIENCE_NOT_SET) == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente Coscienza ALERT
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteCoscienzaALERT() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_coscienza_ALERT.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.coscienza.compareTo(DataModelEnums.COSCIENCE_ALERT) == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente Coscienza VERBAL
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteCodiceVERBAL() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_coscienza_VERBAL.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.coscienza.compareTo(DataModelEnums.COSCIENCE_VERBAL) == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente Coscienza PAIN
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteCoscienzaPAIN() throws JAXBException
  {
	  logger.trace(this.getName());
	  
  		String test_file = "test_paziente_coscienza_PAIN.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.coscienza.compareTo(DataModelEnums.COSCIENCE_PAIN) == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente Coscience UNRESPONSIVE
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteCoscienceUNRESPONSIVE() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_coscienza_UNRESPONSIVE.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.coscienza.compareTo(DataModelEnums.COSCIENCE_UNRESPONSIVE) == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente Coscienza Empty
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteCoscienzaEmpty() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_coscienza_empty.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.coscienza == "");
  }
  
  /** Test Membro Unmarshalling - Paziente Coscienza Missing
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteCoscienzaMissing() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_coscienza_missing.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.coscienza == null);
  }
  
  /** Test Membro Unmarshalling - Paziente Coscienza Null
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteCoscienzaNull() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_coscienza_null.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.coscienza == "");
  }
  
  
  
  /** Test Membro Unmarshalling - Paziente Eta Decimal
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteEtaDecimal() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_eta_decimal.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.eta == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente Eta Negative
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteEtaNegative() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_eta_negative.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertFalse( p.validateObject() );
  		assertTrue (p.eta < 0);
  }
  
  /** Test Membro Unmarshalling - Paziente Eta Null
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteEtaNull() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_eta_null.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.eta == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente Eta Missing
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteEtaMissing() throws JAXBException
  {
	  logger.trace(this.getName());
	  
  		String test_file = "test_paziente_eta_missing.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.eta == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente Eta Empty
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteEtaEmpty() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_eta_missing.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.eta == 0);
  }
  
  
  
  /** Test Membro Unmarshalling - Paziente FR NOT SET
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteFRNOTSET() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_FR_NOTSET.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.FR == DataModelEnums.FR_NOT_SET);
  }
  
  /** Test Membro Unmarshalling - Paziente FR NORMALE
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteFRNORMALE() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_FR_NORMALE.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.FR == DataModelEnums.FR_NORMALE);
  }
  
  /** Test Membro Unmarshalling - Paziente FR DIFFICOLTOSO
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteFRDIFFICOLTOSO() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_FR_DIFFICOLTOSO.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.FR == DataModelEnums.FR_DIFFICOLTOSO);
  }
  
  /** Test Membro Unmarshalling - Paziente FR ASSENTE
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteFRASSENTE() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_FR_ASSENTE.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.FR == DataModelEnums.FR_ASSENTE);
  }
  
  /** Test Membro Unmarshalling - Paziente FR Decimal
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteFRDecimal() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_FR_decimal.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.FR == DataModelEnums.FR_NOT_SET);
  }
  
  /** Test Membro Unmarshalling - Paziente FR Negative
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteFRNegative() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_FR_negative.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertFalse( p.validateObject() );
  		assertTrue (p.FR < 0);
  }
  
  /** Test Membro Unmarshalling - Paziente FR OUTOFRANGE
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteFROUTOFRANGE() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_FR_OUTOFRANGE.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertFalse( p.validateObject() );
  		assertTrue (p.FR > 3);
  }
  
  /** Test Membro Unmarshalling - Paziente FR Null
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteFRNull() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_FR_null.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.FR == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente FR Missing
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteFRMissing() throws JAXBException
  {
	  logger.trace(this.getName());
	  
  		String test_file = "test_paziente_FR_missing.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.FR == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente FR Empty
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteFREmpty() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_FR_missing.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.FR == 0);
  }
  
    
  
  /** Test Membro Unmarshalling - Paziente Nome Empty
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteNomeEmpty() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_nome_empty.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.nome == "");
  }
  
  /** Test Membro Unmarshalling - Paziente Nome Missing
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteNomeMissing() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_nome_missing.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.nome == null);
  }
  
  /** Test Membro Unmarshalling - Paziente Nome Null
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteNomeNull() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_nome_null.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.nome == "");
  }
  
    
  
  /** Test Membro Unmarshalling - Paziente Pediatric Yes
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazientePediatricYes() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_pediatric_si.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.pediatrico.compareTo(DataModelEnums.PEDIATRIC_SI) == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente Pediatric No
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazientePediatricNo() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_pediatric_no.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.pediatrico.compareTo(DataModelEnums.PEDIATRIC_NO) == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente Pediatric Null
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazientePediatricNull() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_pediatric_null.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.pediatrico == "");
  }
  
  /** Test Membro Unmarshalling - Paziente Pediatric Null
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazientePediatricMissing() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_pediatric_missing.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.pediatrico == null);
  }
  
  /** Test Membro Unmarshalling - Paziente Pediatric Empty
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazientePediatricEmpty() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_pediatric_missing.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.pediatrico == null);
  }
  
  
  
  /** Test Membro Unmarshalling - Tratta
   * @throws JAXBException */
  public void testTrattaUnmarshallingTratta() throws JAXBException
  {
	  logger.trace(this.getName());
	  
	   //Tratta paperino = new Tratta(1, "Duck", null);
	   
  		String test_file = "test_tratta.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Tratta.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Tratta t = (Tratta) jaxbUnmarshaller.unmarshal( new File(test_folder + "/" + test_file) );
  		t.toXML_print();
  		assertTrue( t.validateObject() );
  }
   
  
}
