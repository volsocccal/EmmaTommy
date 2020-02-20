package emmaTommy.EmmaDataModel;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import emmaTommy.EmmaDataModel.EmmaDataModelEnums;
import emmaTommy.EmmaDataModel.Paziente;
import emmaTommy.EmmaDataModel.Pazienti;
import junit.framework.Test;
import junit.framework.TestSuite;

public class XMLParserTestPaziente extends XMLParserTest {
	
	
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public XMLParserTestPaziente( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( XMLParserTestPaziente.class );
    }

  
  /** Test Membro Unmarshalling - Paziente Generic Donald Duck
   * @throws JAXBException */
  @SuppressWarnings("deprecation")
  public void testPazienteUnmarshallingPazienteGenericDonaldDuck() throws JAXBException
  {
	  logger.trace(this.getName());
	  
	   Paziente paperino = new Paziente("Donald", "Duck", EmmaDataModelEnums.MALE_GENDER,
			    (new GregorianCalendar(1980, Calendar.FEBRUARY, 11).getTime()), 39, 
				EmmaDataModelEnums.PEDIATRIC_NO,
				EmmaDataModelEnums.OUTCOME_TRASPORTO_PAZIENTE_REGOLARE,
				EmmaDataModelEnums.CODES_VERDE,
				60, EmmaDataModelEnums.FR_NORMALE,
				EmmaDataModelEnums.COSCIENCE_ALERT,
				"Paperopoli");
	   
  		String test_file = "test_paziente_generic_donald_duck.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
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
  @SuppressWarnings("deprecation")
  public void testPazienteUnmarshallingPazienteGenericMinnieMouse() throws JAXBException
  {
	  logger.trace(this.getName());
	  
	   Paziente minnie = new Paziente("Minnie", "Mouse", EmmaDataModelEnums.FEMALE_GENDER,
			    (new GregorianCalendar(1950, Calendar.JUNE, 3).getTime()), 69, 
				EmmaDataModelEnums.PEDIATRIC_NO,
				EmmaDataModelEnums.OUTCOME_TRASPORTO_PAZIENTE_RIFIUTO_TRASPORTO,
				EmmaDataModelEnums.CODES_GIALLO,
				100, EmmaDataModelEnums.FR_DIFFICOLTOSO,
				EmmaDataModelEnums.COSCIENCE_VERBAL,
				"Topolinia");
	   
  		String test_file = "test_paziente_generic_minnie_mouse.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
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
	  
	   Paziente minimal = new Paziente(EmmaDataModelEnums.OUTCOME_TRASPORTO_PAZIENTE_REGOLARE);
	   
  		String test_file = "test_paziente_generic_minimal.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
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
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.codicePazienteArrivo.compareTo(EmmaDataModelEnums.CODES_ROSSO) == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente Codice Giallo
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteCodiceGiallo() throws JAXBException
  {
	  logger.trace(this.getName());
	  	   
  		String test_file = "test_paziente_codice_giallo.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.codicePazienteArrivo.compareTo(EmmaDataModelEnums.CODES_GIALLO) == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente Codice Verde
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteCodiceVerde() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_codice_verde.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.codicePazienteArrivo.compareTo(EmmaDataModelEnums.CODES_VERDE) == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente Codice Blu
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteCodiceBlu() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_codice_blu.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
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
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
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
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
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
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
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
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
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
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
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
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
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
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
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
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
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
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
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
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.coscienza.compareTo(EmmaDataModelEnums.COSCIENCE_NOT_SET) == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente Coscienza ALERT
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteCoscienzaALERT() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_coscienza_ALERT.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.coscienza.compareTo(EmmaDataModelEnums.COSCIENCE_ALERT) == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente Coscienza VERBAL
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteCodiceVERBAL() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_coscienza_VERBAL.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.coscienza.compareTo(EmmaDataModelEnums.COSCIENCE_VERBAL) == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente Coscienza PAIN
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteCoscienzaPAIN() throws JAXBException
  {
	  logger.trace(this.getName());
	  
  		String test_file = "test_paziente_coscienza_PAIN.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.coscienza.compareTo(EmmaDataModelEnums.COSCIENCE_PAIN) == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente Coscience UNRESPONSIVE
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteCoscienceUNRESPONSIVE() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_coscienza_UNRESPONSIVE.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.coscienza.compareTo(EmmaDataModelEnums.COSCIENCE_UNRESPONSIVE) == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente Coscienza Empty
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteCoscienzaEmpty() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_coscienza_empty.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
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
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
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
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.coscienza == "");
  }
  
  
  
  /** Test Membro Unmarshalling - Paziente Esito Deceduto
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteEsitoDeceduto() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_esito_trasporto_deceduto.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
  		assertTrue( p.validateObject() );
  		assertTrue (p.esitoTrasporto.compareTo(EmmaDataModelEnums.OUTCOME_TRASPORTO_PAZIENTE_DECEDUTO) == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente Esito NC
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteEsitoNC() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_esito_trasporto_NC.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
  		assertTrue( p.validateObject() );
  		assertTrue (p.esitoTrasporto.compareTo(EmmaDataModelEnums.OUTCOME_TRASPORTO_PAZIENTE_NC) == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente Esito Non Necessita
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteEsitoNonNecessita() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_esito_trasporto_non_necessita.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
  		assertTrue( p.validateObject() );
  		assertTrue (p.esitoTrasporto.compareTo(EmmaDataModelEnums.OUTCOME_TRASPORTO_PAZIENTE_NON_NECESSITA) == 0);
  }
  
  
  /** Test Membro Unmarshalling - Paziente Esito Non Rinvenuto
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteEsitoNonRinvenuto() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_esito_trasporto_non_rinvenuto.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
  		assertTrue( p.validateObject() );
  		assertTrue (p.esitoTrasporto.compareTo(EmmaDataModelEnums.OUTCOME_TRASPORTO_PAZIENTE_NON_RINVENUTO) == 0);
  }
  
  
  /** Test Membro Unmarshalling - Paziente Esito Regolare
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteEsitoRegolare() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_esito_trasporto_regolare.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
  		assertTrue( p.validateObject() );
  		assertTrue (p.esitoTrasporto.compareTo(EmmaDataModelEnums.OUTCOME_TRASPORTO_PAZIENTE_REGOLARE) == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente Esito Si Allontana
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteEsitoAllontana() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_esito_trasporto_si_allontana.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
  		assertTrue( p.validateObject() );
  		assertTrue (p.esitoTrasporto.compareTo(EmmaDataModelEnums.OUTCOME_TRASPORTO_PAZIENTE_ALLONTANA) == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente Esito Rifiuto Trasporto 
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteEsitoRifiutoTrasporto() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_esito_trasporto_rifiuto_trasporto.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
  		assertTrue( p.validateObject() );
  		assertTrue (p.esitoTrasporto.compareTo(EmmaDataModelEnums.OUTCOME_TRASPORTO_PAZIENTE_RIFIUTO_TRASPORTO) == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente Esito Trattenuto  
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteEsitoTrattenuto() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_esito_trasporto_trattenuto.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
  		assertTrue( p.validateObject() );
  		assertTrue (p.esitoTrasporto.compareTo(EmmaDataModelEnums.OUTCOME_TRASPORTO_PAZIENTE_TRATTENUTO) == 0);
  }
  
  
  
  
  /** Test Membro Unmarshalling - Paziente Esito Empty
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteEsitoEmpty() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_esito_trasporto_empty.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
  		p.toXML_print();
  		assertFalse( p.validateObject() );
  		assertTrue (p.esitoTrasporto == "");
  }
  
  /** Test Membro Unmarshalling - Paziente Esito Missing
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteEsitoMissing() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_esito_trasporto_missing.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
  		p.toXML_print();
  		assertFalse( p.validateObject() );
  		assertTrue (p.esitoTrasporto == null);
  }
  
  /** Test Membro Unmarshalling - Paziente Esito Null
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteEsitoNull() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_esito_trasporto_null.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
  		p.toXML_print();
  		assertFalse( p.validateObject() );
  		assertTrue (p.esitoTrasporto == "");
  }
  
  
  
  
  
  
  
  
  /** Test Membro Unmarshalling - Paziente Eta Decimal
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteEtaDecimal() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_eta_decimal.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
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
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
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
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
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
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
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
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
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
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.FR == EmmaDataModelEnums.FR_NOT_SET);
  }
  
  /** Test Membro Unmarshalling - Paziente FR NORMALE
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteFRNORMALE() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_FR_NORMALE.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.FR == EmmaDataModelEnums.FR_NORMALE);
  }
  
  /** Test Membro Unmarshalling - Paziente FR DIFFICOLTOSO
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteFRDIFFICOLTOSO() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_FR_DIFFICOLTOSO.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.FR == EmmaDataModelEnums.FR_DIFFICOLTOSO);
  }
  
  /** Test Membro Unmarshalling - Paziente FR ASSENTE
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteFRASSENTE() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_FR_ASSENTE.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.FR == EmmaDataModelEnums.FR_ASSENTE);
  }
  
  /** Test Membro Unmarshalling - Paziente FR Decimal
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteFRDecimal() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_FR_decimal.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.FR == EmmaDataModelEnums.FR_NOT_SET);
  }
  
  /** Test Membro Unmarshalling - Paziente FR Negative
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazienteFRNegative() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_FR_negative.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
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
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
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
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
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
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
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
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
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
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
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
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
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
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
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
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.pediatrico.compareTo(EmmaDataModelEnums.PEDIATRIC_SI) == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente Pediatric No
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazientePediatricNo() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_pediatric_no.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.pediatrico.compareTo(EmmaDataModelEnums.PEDIATRIC_NO) == 0);
  }
  
  /** Test Membro Unmarshalling - Paziente Pediatric Null
   * @throws JAXBException */
  public void testPazienteUnmarshallingPazientePediatricNull() throws JAXBException
  {
	  logger.trace(this.getName());
	   
  		String test_file = "test_paziente_pediatric_null.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Paziente.class);
  		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
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
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
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
  		Paziente p = (Paziente) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
  		p.toXML_print();
  		assertTrue( p.validateObject() );
  		assertTrue (p.pediatrico == null);
  }
  
  

  /** Test Paziente Unmarshalling - Pazienti Full List
   * @throws JAXBException */
  public void testPazientiUnmarshallingList() throws JAXBException
  {
	  	logger.trace(this.getName());
	  
	  	Paziente paperino = new Paziente("Donald", "Duck", EmmaDataModelEnums.MALE_GENDER,
			    (new GregorianCalendar(1980, Calendar.FEBRUARY, 11).getTime()), 39, 
				EmmaDataModelEnums.PEDIATRIC_NO,
				EmmaDataModelEnums.OUTCOME_TRASPORTO_PAZIENTE_REGOLARE,
				EmmaDataModelEnums.CODES_VERDE,
				60, EmmaDataModelEnums.FR_NORMALE,
				EmmaDataModelEnums.COSCIENCE_ALERT,
				"Paperopoli");
	  	
	  	Paziente qui = new Paziente("Huey", "Duck", EmmaDataModelEnums.MALE_GENDER,
			    (new GregorianCalendar(2010, Calendar.OCTOBER, 17).getTime()), 9, 
				EmmaDataModelEnums.PEDIATRIC_SI,
				EmmaDataModelEnums.OUTCOME_TRASPORTO_PAZIENTE_REGOLARE,
				EmmaDataModelEnums.CODES_GIALLO,
				60, EmmaDataModelEnums.FR_NORMALE,
				EmmaDataModelEnums.COSCIENCE_VERBAL,
				"Paperopoli");
	  	
	  	Paziente quo = new Paziente("Dewey", "Duck", EmmaDataModelEnums.MALE_GENDER,
			    (new GregorianCalendar(2010, Calendar.OCTOBER, 17).getTime()), 9, 
				EmmaDataModelEnums.PEDIATRIC_SI,
				EmmaDataModelEnums.OUTCOME_TRASPORTO_PAZIENTE_REGOLARE,
				EmmaDataModelEnums.CODES_VERDE,
				60, EmmaDataModelEnums.FR_NORMALE,
				EmmaDataModelEnums.COSCIENCE_ALERT,
				"Paperopoli");
	  	
	  	Paziente qua = new Paziente("Louie", "Duck", EmmaDataModelEnums.MALE_GENDER,
			    (new GregorianCalendar(2010, Calendar.OCTOBER, 17).getTime()), 9, 
				EmmaDataModelEnums.PEDIATRIC_SI,
				EmmaDataModelEnums.OUTCOME_TRASPORTO_PAZIENTE_REGOLARE,
				EmmaDataModelEnums.CODES_VERDE,
				60, EmmaDataModelEnums.FR_NORMALE,
				EmmaDataModelEnums.COSCIENCE_ALERT,
				"Paperopoli");
	  	
	  	Paziente paperina = new Paziente("Daisy", "Duck", EmmaDataModelEnums.FEMALE_GENDER,
			    (new GregorianCalendar(1979, Calendar.JUNE, 28).getTime()), 40, 
				EmmaDataModelEnums.PEDIATRIC_NO,
				EmmaDataModelEnums.OUTCOME_TRASPORTO_PAZIENTE_REGOLARE,
				EmmaDataModelEnums.CODES_VERDE,
				60, EmmaDataModelEnums.FR_NORMALE,
				EmmaDataModelEnums.COSCIENCE_ALERT,
				"Paperopoli");
		
		String test_file = "test_pazienti.xml";
		JAXBContext jaxbContext = JAXBContext.newInstance(Pazienti.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Pazienti pazienti = (Pazienti) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
		pazienti.getPazienti().get(0).equals(paperino);
		pazienti.getPazienti().get(1).equals(qui);
		pazienti.getPazienti().get(2).equals(quo);
		pazienti.getPazienti().get(3).equals(qua);
		pazienti.getPazienti().get(4).equals(paperina);
		pazienti.toJSON_print();
  }
  
  /** Test Paziente Unmarshalling - Pazienti Singleton List
   * @throws JAXBException */
  public void testPazientiUnmarshallingSingletonList() throws JAXBException
  {
	  	logger.trace(this.getName());
	  
	  	Paziente paperino = new Paziente("Donald", "Duck", EmmaDataModelEnums.MALE_GENDER,
			    (new GregorianCalendar(1980, Calendar.FEBRUARY, 11).getTime()), 39, 
				EmmaDataModelEnums.PEDIATRIC_NO,
				EmmaDataModelEnums.OUTCOME_TRASPORTO_PAZIENTE_REGOLARE,
				EmmaDataModelEnums.CODES_VERDE,
				60, EmmaDataModelEnums.FR_NORMALE,
				EmmaDataModelEnums.COSCIENCE_ALERT,
				"Paperopoli");
		
		String test_file = "test_pazienti_singleton.xml";
		JAXBContext jaxbContext = JAXBContext.newInstance(Pazienti.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Pazienti pazienti = (Pazienti) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
		pazienti.getPazienti().get(0).equals(paperino);
		assertTrue( pazienti.validateObject() );
  }
      
  /** Test Paziente Unmarshalling - Pazienti Empty List
   * @throws JAXBException */
  public void testPazientiUnmarshallingEmptyList() throws JAXBException
  {
	  	logger.trace(this.getName());
	  	
  		String test_file = "test_pazienti_empty_array.xml";
  		JAXBContext jaxbContext = JAXBContext.newInstance(Pazienti.class);
	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	  	Pazienti pazienti = (Pazienti) jaxbUnmarshaller.unmarshal( new File(test_data_folder_paziente + "/" + test_file) );
	  	assertFalse( pazienti.validateObject() );	
  }
  
  
  
 
}
