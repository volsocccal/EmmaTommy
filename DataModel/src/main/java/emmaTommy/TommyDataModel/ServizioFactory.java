package emmaTommy.TommyDataModel;

import java.io.StringReader;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.eclipse.persistence.oxm.MediaType;

import emmaTommy.EmmaDataModel.Missione;
import emmaTommy.EmmaDataModel.Missioni;

public class ServizioFactory {
	
	AssistitiFactory assistitiFact;
	SquadraFactory squadraFact;
	
	/** Empty Constructor */
	public ServizioFactory() {
		this.assistitiFact = new AssistitiFactory();
		this.squadraFact = new SquadraFactory();
	}
	
	public Servizio buildServizio() {
		return new Servizio();
	}
	
	public Servizio buildServizio(Missione m) {
		
		// Check for null servizio
		if (m == null) {
			return null;
		}
		
		//
		Servizio s = new Servizio();
		
		// "data" : "type:YYYYMMDD:required"
		s.setData(m.getInizioMissione());
		
		// "codice_servizio" : "type:String:required"
		s.setCodiceServizio(m.getCodiceEvento());
		
		// orario_inizio_servizio" : "type:HH:II:required"
		s.setOrarioInizioServizio(m.getInizioMissione());
		
		/**
		// orario_arrivo_posto" : "type:HH:II"		
		m.getTratte().getTratte().get(0)
		s.setOrarioArrivoPosto();
		// orario_partenza_posto" : "type:HH:II"
		s.OrarioPartenzaPosto();
		*/
		
		
		
		/**
		// orario_arrivo_ospedale" : "type:HH:II"
		s.orarioArrivoOspedale(); 
		// orario_partenza_ospedale" : "type:HH:II"
		s.setOrarioPartenzaOspedale();
		 */
		
		
		
		// orario_fine_servizio" : "type:HH:II:required"
		s.setOrarioFineServizio(m.getFineMissione());
		    	
		// tag_idintervento" : "type:String:required"
		s.setTagIdIntervento(this.tipoEventoConverter(m.getConvenzioneEnte()));
		
		// tag_idautomezzo" : "type:String:required"
		s.setTagIdAutomezzo(m.getCodiceMezzo());
		
		// km" : "type:Int:required"
		s.setKM(m.getTotKMPercorsi());
		
		// luogo_partenza" : "type:String"
		String luogoPartenza = "";
		if (m.getPartenzaMissioneLocalita() != null) {
			if (!m.getPartenzaMissioneLocalita().isBlank()) {
				luogoPartenza += m.getPartenzaMissioneLocalita();
				if (m.getPartenzaMissioneVia() != null) {
					if (!m.getPartenzaMissioneVia().isBlank()) {
						luogoPartenza += " " + m.getPartenzaMissioneVia();
					}
				}
			}
		}
		s.setLuogoPartenza(luogoPartenza);
		
		// luogo_arrivo" : "type:String"
		String luogoEvento = "";
		if (m.getComuneIntervento() != null) {
			if (!m.getComuneIntervento().isBlank()) {
				luogoPartenza += m.getComuneIntervento();
				if (m.getIndirizzoSoccorsoTipoVia() != null) {
					if (!m.getIndirizzoSoccorsoTipoVia().isBlank()) {
						luogoPartenza += " " + m.getIndirizzoSoccorsoTipoVia();
					}
				}
				if (m.getIndirizzoSoccorsoVia() != null) {
					if (!m.getIndirizzoSoccorsoVia().isBlank()) {
						luogoPartenza += " " + m.getIndirizzoSoccorsoVia();
						if (m.getIndirizzoSoccorsoCivico() != null) {
							if (!m.getIndirizzoSoccorsoCivico().isBlank()) {
								luogoPartenza += " " + m.getIndirizzoSoccorsoCivico();
								if (m.getIndirizzoSoccorsoCivicoBarra() != null) {
									if (!m.getIndirizzoSoccorsoCivicoBarra().isBlank()) {
										luogoPartenza += " " + m.getIndirizzoSoccorsoCivicoBarra();
									}
								}
							}
						}
						if (m.getIndirizzoSoccorsoViaIncrocio() != null) {
							if (!m.getIndirizzoSoccorsoViaIncrocio().isBlank()) {
								luogoPartenza += " " + m.getIndirizzoSoccorsoViaIncrocio();
							}
						}
					}
				}
			}
		}
		s.setLuogoArrivo(luogoEvento);
		
		// Assistiti
		s.setAssistiti(this.assistitiFact.buildAssistiti(m.getPazienti()));
		
		// Squadra
		s.setSquadra(this.squadraFact.buildSquadra(m.getMembri()));
		
		// Validate Servizio
		s.validateObject();
		
		return s;
	}
	
	public Servizio buildServizioUnmarshallJSON(String json) throws JAXBException {
		
		// Create String Reader
		StringReader jsonReader = new StringReader(json);
						
		// Unmarshaller
		JAXBContext jaxbContext = JAXBContext.newInstance(Servizio.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		jaxbUnmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
        jaxbUnmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, true);
		Servizio s = (Servizio) jaxbUnmarshaller.unmarshal(jsonReader);
		if (s == null) {
			throw new NullPointerException("Unmarshalled Servizio Object was nullptr");
		}
		
		// Validate Object
		s.validateObject();
		
		// Return Missioni
		return s;
		
	}
	
	public Servizio buildServizioUnmarshallXML(String xml) throws JAXBException {
		
		// Create String Reader
		StringReader xmlReader = new StringReader(xml);
				
		// Unmarshaller
		JAXBContext jaxbContext = JAXBContext.newInstance(Servizio.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Servizio s = (Servizio) jaxbUnmarshaller.unmarshal(xmlReader);
		if (s == null) {
			throw new NullPointerException("Unmarshalled Servizio Object was nullptr");
		}
		
		// Validate Object
		s.validateObject();
		
		// Return Missioni
		return s;
		
	}
	
	protected String tipoEventoConverter (String tipoEventoMissioni) {
		if (tipoEventoMissioni == null) {
			return "NULL tipoEventoMissioni";
		}
		if (tipoEventoMissioni == "" || tipoEventoMissioni.isEmpty() || tipoEventoMissioni.isBlank()) {
			return "EMPTY tipoEventoMissioni";
		}
		if (TommyDataModelEnums.tipoEventoConversion.containsKey(tipoEventoMissioni)) {
			return TommyDataModelEnums.tipoEventoConversion.get(tipoEventoMissioni);
		} else {
			return "NOT_FOUND: " + tipoEventoMissioni;
		}
	}

}
