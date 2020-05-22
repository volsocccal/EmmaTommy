package emmaTommy.TommyDataModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.JAXBException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;

import emmaTommy.TommyDataModel.Factories.ServizioFactory;


@Entity
@Table(name="servizi")
public class TommyEnrichedJSON {

	static org.apache.logging.log4j.Logger logger = LogManager.getLogger("TommyDataModel");
	static String dataFormatDate = "yyyyMMdd"; 
	static String dataFormatTime = "HH:mm"; 
	static String dataFormatDateTime = "yyyy/MM/dd HH:mm:ss"; 
	
	/** JSON Service Field */
	protected String jsonServizio;
	public String getJsonServizio() {
		return this.jsonServizio;	
	}
	public void setJsonServizio(String jsonServizio) throws IllegalArgumentException {
		String method_name = "::setJsonServizio(): ";
		if (jsonServizio == null) {
			throw new NullPointerException(method_name + "Input Servizio was nullptr");
		}
		String oldJsonServizio = this.jsonServizio;
		try {
			this.jsonServizio = jsonServizio;
			Servizio s = this.buildServizio();
			if (this.codiceServizio == null) {
				this.codiceServizio = s.getCodiceServizio();
			}
			if (this.codiceServizio.compareTo(s.getCodiceServizio()) != 0) {
				throw new IllegalArgumentException("Given Codice Servizio was " + this.codiceServizio
													+ " but in the input JSON the CodiceServizio was " + s.getCodiceServizio());
			} else {
				this.setCodiceMezzo(s.getTagIdAutomezzo());
				this.setKm(s.getKM());
				this.setMissioneStartDate(s.getMissioneDate());
				this.setMissioneStartTime(s.getOrarioInizioServizio());
				this.setTimeStamp(LocalDateTime.now());
				this.initializedFlag = true;				
			}			
		} catch (JAXBException e) {
			this.jsonServizio = oldJsonServizio;
			throw new IllegalArgumentException("Failed to Unmarshall the given JSON: " + e.getMessage());			
		} catch (IllegalArgumentException e) {
			this.jsonServizio = oldJsonServizio;
			throw e;		
		} catch (Exception e) {
			this.jsonServizio = oldJsonServizio;
			throw e;		
		}
		
	}
	
	/** Number of KM for the Servizio */
	protected int km;
	public int getKm() {
		return this.km;
	}
	public void setKm(int km) {
		String method_name = "::setKm(): ";
		if (km < 0) {
			throw new IllegalArgumentException(method_name + "Input km was nullptr");
		}
		this.km = km;
	}
	
	/** Servizio ID */
	@Id
	@Column(name="codice_servizio")
	protected String codiceServizio;
	public String getCodiceServizio() {
		return this.codiceServizio;
	}
	public void setCodiceServizio(String codiceServizio) {
		this.codiceServizio = codiceServizio;
	}
	
	/** Mezzo ID */
	@Column(name="codice_mezzo")
	protected String codiceMezzo;
	public String getCodiceMezzo() {
		return this.codiceMezzo;
	}
	public void setCodiceMezzo(String codiceMezzo) {
		this.codiceMezzo = codiceMezzo;
	}
	
	/** Missione StartDate */
	@Column(name="start_date")
	protected LocalDate missioneStartDate;
	public LocalDate getMissioneStartDate() {
		return this.missioneStartDate;
	}
	public String getMissioneStartDateStr() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dataFormatDate); 
		return dtf.format(this.missioneStartDate);
	}
	public void setMissioneStartDate(LocalDate localDate) {
		this.missioneStartDate = localDate;
	}
	
	/** Missione StartTime */
	@Column(name="start_time")
	protected LocalTime missioneStartTime;
	public LocalTime getMissioneStartTime() {
		return this.missioneStartTime;
	}
	public String getMissioneStartTimeStr() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dataFormatTime); 
		return dtf.format(this.missioneStartTime);
	}
	public void setMissioneStartTime(LocalTime localStartTime) {
		if (localStartTime == null) {
			logger.error("TommyEnrichedJSON::setMissioneStartTime(): " + "Received null MissioneStartTime");
		}
		this.missioneStartTime = localStartTime;
	}
	
	/** Object TimeStamp */
	@Column(name="timeStamp")
	protected LocalDateTime timeStamp;
	public LocalDateTime getTimeStamp() {
		return this.timeStamp;
	}
	public String getTimeStampStr() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dataFormatDateTime); 
		return dtf.format(this.timeStamp);
	}
	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	/** Initialized Flag */
	protected Boolean initializedFlag; 
	public Boolean isInitialized() {
		return this.initializedFlag;
	}
	
	public TommyEnrichedJSON() {
		this.jsonServizio = null;
		this.codiceMezzo = null;
		this.codiceServizio = null;
		this.km = -1;
		this.missioneStartDate = null; 
		this.timeStamp = LocalDateTime.now(); 
		this.initializedFlag = false;
	}
	
	public TommyEnrichedJSON (String codiceServizio, String jsonServizio) {
		this();
		this.setCodiceServizio(codiceServizio);
		this.setJsonServizio(jsonServizio);
	}
	
	public Servizio buildServizio() throws JAXBException {
		ServizioFactory sFact = new ServizioFactory();
		Servizio s = sFact.buildServizioUnmarshallJSON(jsonServizio);
		return s;
	}

	

	

	

	
	
}
