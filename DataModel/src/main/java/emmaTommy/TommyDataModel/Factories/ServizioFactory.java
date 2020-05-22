package emmaTommy.TommyDataModel.Factories;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.eclipse.persistence.oxm.MediaType;

import emmaTommy.EmmaDataModel.Missione;
import emmaTommy.EmmaDataModel.Tratta;
import emmaTommy.EmmaDataModel.Tratte;
import emmaTommy.TommyDataModel.Servizio;
import emmaTommy.TommyDataModel.TommyDataModelEnums;

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
		
		// BUild new Servizio
		Servizio s = new Servizio();
		
		// "data" : "type:YYYYMMDD:required"
		s.setData(m.getInizioMissione().toLocalDate());
		
		// "codice_servizio" : "type:String:required"
		s.setCodiceServizio(Integer.toString(m.getCodiceMissione()));
		
		// orario_inizio_servizio" : "type:HH:II:required"
		s.setOrarioInizioServizio(m.getInizioMissione().toLocalTime());
		
		// Get Tratte
		Tratte tr = m.getTratte();
		if (tr != null) {
			ArrayList<Tratta> trList = (ArrayList<Tratta>) tr.getTratte(); 
			if (trList != null) {
				if (!trList.isEmpty()) {
					if (trList.size() == 1) {
						Tratta tr1 = trList.get(0);
						if (tr1 != null) {
							s.setOrarioArrivoPosto(tr1.getDataArrivo().toLocalTime());
							s.setOrarioPartenzaPosto(tr1.getDataPartenza().toLocalTime());
							//s.setLuogoPartenza(tr1.getDestinazione());
						}
					} else {
						Tratta tr1 = trList.get(0);
						Tratta tr2 = trList.get(1);
						if (tr1 != null) {
							if (tr2 != null) {
								if (tr1.getId() > tr2.getId()) { // Swap if they are in the wrong order
									tr1 = trList.get(1);
									tr2 = trList.get(0);
								}
								s.setOrarioArrivoOspedale(tr2.getDataArrivo().toLocalTime());
								s.setOrarioPartenzaOspedale(tr2.getDataPartenza().toLocalTime());
								s.setLuogoArrivo(tr2.getDestinazione());
							}
							s.setOrarioArrivoPosto(tr1.getDataArrivo().toLocalTime());
							s.setOrarioPartenzaPosto(tr1.getDataPartenza().toLocalTime());
							//s.setLuogoPartenza(tr1.getDestinazione());
						}
					}
				}
			}
		}
		
		// orario_fine_servizio" : "type:HH:II:required"
		s.setOrarioFineServizio(m.getFineMissione().toLocalTime());
		    	
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
				luogoEvento += m.getComuneIntervento();
				if (m.getIndirizzoSoccorsoTipoVia() != null) {
					if (!m.getIndirizzoSoccorsoTipoVia().isBlank()) {
						luogoEvento += " " + m.getIndirizzoSoccorsoTipoVia();
					}
				}
				if (m.getIndirizzoSoccorsoVia() != null) {
					if (!m.getIndirizzoSoccorsoVia().isBlank()) {
						luogoEvento += " " + m.getIndirizzoSoccorsoVia();
						if (m.getIndirizzoSoccorsoCivico() != null) {
							if (!m.getIndirizzoSoccorsoCivico().isBlank()) {
								luogoEvento += " " + m.getIndirizzoSoccorsoCivico();
								if (m.getIndirizzoSoccorsoCivicoBarra() != null) {
									if (!m.getIndirizzoSoccorsoCivicoBarra().isBlank()) {
										luogoEvento += " " + m.getIndirizzoSoccorsoCivicoBarra();
									}
								}
							}
						}
						if (m.getIndirizzoSoccorsoViaIncrocio() != null) {
							if (!m.getIndirizzoSoccorsoViaIncrocio().isBlank()) {
								luogoEvento += " " + m.getIndirizzoSoccorsoViaIncrocio();
							}
						}
					}
				}
			}
		}
		//s.setLuogoArrivo(luogoEvento);
		s.setLuogoPartenza(luogoEvento);
		
		// Esito Missione
		String esitoMissione = m.getEsitoMissione();
		if (esitoMissione != null) {
			if (!esitoMissione.isBlank()) {
				if (esitoMissione.compareTo(emmaTommy.EmmaDataModel.EmmaDataModelEnums.MISSIONE_OUTCOME_REGOLARE) != 0) {
					if (esitoMissione.contains("INTERROTTA")) {
						s.setLuogoPartenza("_Missione Interrotta_");
						s.setLuogoArrivo("_Missione Interrotta_");
					}
					else if (esitoMissione.compareTo(emmaTommy.EmmaDataModel.EmmaDataModelEnums.MISSIONE_OUTCOME_EVACUATO_ALS) == 0) {
						s.setLuogoArrivo("_Trasportato da Altro Mezzo_");
					}
					else if (esitoMissione.compareTo(emmaTommy.EmmaDataModel.EmmaDataModelEnums.MISSIONE_OUTCOME_DECEDUTO) == 0) {
						s.setLuogoArrivo("_Deceduto_");
					}
					else if (esitoMissione.compareTo(emmaTommy.EmmaDataModel.EmmaDataModelEnums.MISSIONE_OUTCOME_REGOLARE_NON_TRASPORTA) == 0) {
						s.setLuogoArrivo("_Non Trasportato su Indicazione SOREU/Medico_");
					}
					else if (esitoMissione.compareTo(emmaTommy.EmmaDataModel.EmmaDataModelEnums.MISSIONE_OUTCOME_SI_ALLONTANA) == 0) {
						s.setLuogoArrivo("_Paziente Si Allontana_");
					}
					else if (esitoMissione.compareTo(emmaTommy.EmmaDataModel.EmmaDataModelEnums.MISSIONE_OUTCOME_VUOTO) == 0) {
						s.setLuogoArrivo("_Paziente non Trovato_");
					}
					else if (esitoMissione.compareTo(emmaTommy.EmmaDataModel.EmmaDataModelEnums.MISSIONE_OUTCOME_NON_TRASPORTA) == 0) {
						s.setLuogoArrivo("_Rifiuto Ricovero_");
					}
					else {
						s.setLuogoArrivo("_Altro_");
					}
					
				}
			}
		}
		
		// Assistiti
		s.setAssistiti(this.assistitiFact.buildAssistiti(m.getPazienti()));
		
		// Squadra
		s.setSquadra(this.squadraFact.buildSquadra(m.getMembri()));
		
		// Codice Evento
		String codiceEvento = m.getCodiceEvento();
		if (codiceEvento == null || codiceEvento.isBlank()) {
			codiceEvento = "Non Noto";
		}
		
		// Codice Trasporto
		String codiceTrasporto = m.getCodiceTrasporto();
		if (codiceTrasporto == null || codiceTrasporto.isBlank()) {
			codiceTrasporto = "Non Noto";
		}
		
		// Motivo Chiamata
		String motivoChiamata = m.getMotivoChiamata();
		if (motivoChiamata == null || motivoChiamata.isBlank()) {
			motivoChiamata = "Non Noto";
		} else {
			String motivoDettaglio = m.getMotivoChiamataDettaglio();
			if (motivoDettaglio != null) {
				if (!motivoDettaglio.isBlank()) {
					motivoChiamata += " " + motivoDettaglio;
				}
			}			
		}
				
		// Set Note		
		String noteServizio = "Invio Codice: " + codiceEvento + "\n"
							+ "Motivo Chiamata: " + motivoChiamata + "\n"
							+ "Trasporto Codice: " + codiceTrasporto + "\n"
							+ "Esito Missione: " + esitoMissione;
		s.setNote(noteServizio);
		
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
		
		// Return Servizio
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
		
		// Return Servizio
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
