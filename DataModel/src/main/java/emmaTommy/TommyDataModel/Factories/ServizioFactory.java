package emmaTommy.TommyDataModel.Factories;

import java.io.StringReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.management.RuntimeErrorException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.eclipse.persistence.oxm.MediaType;

import emmaTommy.DataModel.DateAdapterYYMMDD;
import emmaTommy.DataModel.DateTimeAdapterHHmm;
import emmaTommy.EmmaDataModel.Missione;
import emmaTommy.EmmaDataModel.Tratta;
import emmaTommy.EmmaDataModel.Tratte;
import emmaTommy.TommyDataModel.Assistito;
import emmaTommy.TommyDataModel.Membro;
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
		LocalDateTime fineMissione = m.getFineMissione();
		if (fineMissione != null)
			s.setOrarioFineServizio(fineMissione.toLocalTime());
		else
			s.setOrarioFineServizio(null);
		    	
		// tag_idintervento" : "type:String:required"
		s.setTagIdIntervento(this.tipoEventoConverter(m.getConvenzioneEnte()));
		
		// tag_idautomezzo" : "type:String:required"
		s.setTagIdAutomezzo(m.getCodiceMezzo());
		
		// km" : "type:Int:required"
		s.setKM(m.getTotKMPercorsiInt());
		
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
					else if (esitoMissione.compareTo(emmaTommy.EmmaDataModel.EmmaDataModelEnums.MISSIONE_OUTCOME_ERRORE_OPERATIVO) == 0) {
						s.setLuogoArrivo("_Altro_");
						if (m.getTotKMPercorsiInt() < 0)
							s.setKM(0);
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
	

	public Boolean checkProperties(TreeMap<ServizioQueryField, String> propNVmap, Servizio s) {
		
		Boolean checkPassed = false;
		if (propNVmap.isEmpty())
			checkPassed = true;
		for (ServizioQueryField key: propNVmap.keySet()) {
			if (key == ServizioQueryField.assistitoFemale) {
				for (Assistito ass: s.getAssistiti()) {
					if (TommyDataModelEnums.FEMALE_GENDER.compareTo(ass.getSesso()) == 0) {
						checkPassed = true;
					}
				}
			} else if (key == ServizioQueryField.assistitoMale) {
				for (Assistito ass: s.getAssistiti()) {
					if (TommyDataModelEnums.MALE_GENDER.compareTo(ass.getSesso()) == 0) {
						checkPassed = true;
					}
				}
			} else if (key == ServizioQueryField.codice_servizio) {
				String codiceServizio = propNVmap.get(key);
				if (codiceServizio == null)
					throw new NullPointerException("Wanted property " + key + " has a null value");
				checkPassed = codiceServizio.compareTo(s.getCodiceServizio()) == 0;
			} else if (key == ServizioQueryField.equipaggio_0) {
				checkPassed = s.getSquadra().size() == 0;
			} else if (key == ServizioQueryField.equipaggio_1) {
				checkPassed = s.getSquadra().size() == 1;
			} else if (key == ServizioQueryField.equipaggio_2) {
				checkPassed = s.getSquadra().size() == 2;
			} else if (key == ServizioQueryField.equipaggio_3) {
				checkPassed = s.getSquadra().size() == 3;
			} else if (key == ServizioQueryField.equipaggio_4) {
				checkPassed = s.getSquadra().size() == 4;
			} else if (key == ServizioQueryField.equipaggio_trainee) {
				for (Membro m: s.getSquadra()) {
					String qualifica = m.getTagIdQualifica();
					if (qualifica != null)
						if (TommyDataModelEnums.SOCCORRITORE_ADD.compareTo(qualifica) == 0)
							checkPassed = true;
				}
			} else if (key == ServizioQueryField.km) {
				String kmStr = propNVmap.get(key);
				if (kmStr == null)
					throw new NullPointerException("Wanted property " + key + " has a null value");
				int km = Integer.parseInt(kmStr);
				if (km == s.getKM())
					checkPassed = true;
			} else if (key == ServizioQueryField.luogo_partenza) {
				String luogoPartenza = propNVmap.get(key);
				if (luogoPartenza == null)
					throw new NullPointerException("Wanted property " + key + " has a null value");
				checkPassed = luogoPartenza.compareTo(s.getLuogoPartenza()) == 0;
			} else if (key == ServizioQueryField.luogo_arrivo) {
				String luogoArrivo = propNVmap.get(key);
				if (luogoArrivo == null)
					throw new NullPointerException("Wanted property " + key + " has a null value");
				checkPassed = luogoArrivo.compareTo(s.getLuogoArrivo()) == 0;
			} else if (key == ServizioQueryField.membro_equipaggio) {
				String membroEquipaggioStr = propNVmap.get(key);
				if (membroEquipaggioStr == null)
					throw new NullPointerException("Wanted property " + key + " has a null value");
				for (Membro m: s.getSquadra()) {
					String nomeToCompare = m.getTagIdAnagrafica();
					if (membroEquipaggioStr.contains(","))
					{
						String qualifica = m.getTagIdQualifica();
						nomeToCompare += ", " + qualifica;
					}
					checkPassed = membroEquipaggioStr.compareTo(nomeToCompare) == 0;
				}
			} else if (key == ServizioQueryField.missioneDate) {
				String missioneDateStr = propNVmap.get(key);
				if (missioneDateStr == null)
					throw new NullPointerException("Wanted property " + key + " has a null value");
				DateAdapterYYMMDD dateAdapter = new DateAdapterYYMMDD();
				LocalDate missioneDate = dateAdapter.unmarshal(missioneDateStr);
				checkPassed = missioneDate.compareTo(s.getMissioneDate()) == 0;
			} else if (key == ServizioQueryField.orario_inizio_servizio) {
				String orarioInizioServizioStr = propNVmap.get(key);
				if (orarioInizioServizioStr == null)
					throw new NullPointerException("Wanted property " + key + " has a null value");
				DateTimeAdapterHHmm timeAdapter = new DateTimeAdapterHHmm();
				LocalTime orarioInizioServizio = timeAdapter.unmarshal(orarioInizioServizioStr);
				checkPassed = orarioInizioServizio.compareTo(s.getOrarioInizioServizio()) == 0;
			} else if (key == ServizioQueryField.orario_arrivo_posto) {
				String orarioArrivoPostoStr = propNVmap.get(key);
				if (orarioArrivoPostoStr == null)
					throw new NullPointerException("Wanted property " + key + " has a null value");
				DateTimeAdapterHHmm timeAdapter = new DateTimeAdapterHHmm();
				LocalTime orarioArrivoPosto = timeAdapter.unmarshal(orarioArrivoPostoStr);
				checkPassed = orarioArrivoPosto.compareTo(s.getOrarioArrivoPosto()) == 0;
			} else if (key == ServizioQueryField.orario_partenza_posto) {
				String orarioPartenzaPostoStr = propNVmap.get(key);
				if (orarioPartenzaPostoStr == null)
					throw new NullPointerException("Wanted property " + key + " has a null value");
				DateTimeAdapterHHmm timeAdapter = new DateTimeAdapterHHmm();
				LocalTime orarioPartenzaPosto = timeAdapter.unmarshal(orarioPartenzaPostoStr);
				checkPassed = orarioPartenzaPosto.compareTo(s.getOrarioPartenzaPosto()) == 0;
			} else if (key == ServizioQueryField.orario_arrivo_ospedale) {
				String orarioArrivoHStr = propNVmap.get(key);
				if (orarioArrivoHStr == null)
					throw new NullPointerException("Wanted property " + key + " has a null value");
				DateTimeAdapterHHmm timeAdapter = new DateTimeAdapterHHmm();
				LocalTime orarioArrivoH = timeAdapter.unmarshal(orarioArrivoHStr);
				checkPassed = orarioArrivoH.compareTo(s.getOrarioArrivoOspedale()) == 0;
			} else if (key == ServizioQueryField.orario_partenza_ospedale) {
				String orarioPartenzaHStr = propNVmap.get(key);
				if (orarioPartenzaHStr == null)
					throw new NullPointerException("Wanted property " + key + " has a null value");
				DateTimeAdapterHHmm timeAdapter = new DateTimeAdapterHHmm();
				LocalTime orarioPartenzaH = timeAdapter.unmarshal(orarioPartenzaHStr);
				checkPassed = orarioPartenzaH.compareTo(s.getOrarioPartenzaOspedale()) == 0;
			} else if (key == ServizioQueryField.orario_fine_servizio) {
				String orarioFineServizioStr = propNVmap.get(key);
				if (orarioFineServizioStr == null)
					throw new NullPointerException("Wanted property " + key + " has a null value");
				DateTimeAdapterHHmm timeAdapter = new DateTimeAdapterHHmm();
				LocalTime orarioFineServizio = timeAdapter.unmarshal(orarioFineServizioStr);
				checkPassed = orarioFineServizio.compareTo(s.getOrarioFineServizio()) == 0;
			} else if (key == ServizioQueryField.patient) {
				String patient = propNVmap.get(key);
				if (patient == null)
					throw new NullPointerException("Wanted property " + key + " has a null value");
				for (Assistito ass: s.getAssistiti()) {
					String cognome = ass.getCognome();
					String nome = ass.getNome();
					String patientRead = cognome + ", " + nome;
					if (patientRead.compareTo(patient) == 0)
						checkPassed = true;
				}
			} else if (key == ServizioQueryField.tag_idintervento) {
				String tagIdIntervento = propNVmap.get(key);
				if (tagIdIntervento == null)
					throw new NullPointerException("Wanted property " + key + " has a null value");
				checkPassed = tagIdIntervento.compareTo(s.getTagIntervento()) == 0;
			} else if (key == ServizioQueryField.tag_idautomezzo) {
				String tagIdMezzo = propNVmap.get(key);
				if (tagIdMezzo == null)
					throw new NullPointerException("Wanted property " + key + " has a null value");
				checkPassed = tagIdMezzo.compareTo(s.getTagIdAutomezzo()) == 0;
			} else
			{
				throw new RuntimeException("Received Unknown Servizio Property Name");
			}			
			
			if (!checkPassed)
				return false;
			
		}
		return checkPassed;
		
	}
	public Boolean checkPropertiesUnmarshallJSON(TreeMap<ServizioQueryField, String> propNVmap, String json) throws JAXBException {
		return checkProperties(propNVmap, buildServizioUnmarshallJSON(json));		
	}
	public Boolean checkPropertiesUnmarshallXML(TreeMap<ServizioQueryField, String> propNVmap, String xml) throws JAXBException {
		return checkProperties(propNVmap, buildServizioUnmarshallXML(xml));		
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
