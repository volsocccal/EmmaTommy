package emmaTommy.TommyDataModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name="serviziStaging")
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
		PostStatus oldStatus = this.postStatus;
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
				this.postStatus = PostStatus.POSTING;
				this.initializedFlag = true;		
			}			
		} catch (JAXBException e) {
			this.jsonServizio = oldJsonServizio;
			this.postStatus = oldStatus;
			throw new IllegalArgumentException("Failed to Unmarshall the given JSON: " + e.getMessage());			
		} catch (IllegalArgumentException e) {
			this.jsonServizio = oldJsonServizio;
			this.postStatus = oldStatus;
			throw e;		
		} catch (Exception e) {
			this.jsonServizio = oldJsonServizio;
			this.postStatus = oldStatus;
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
	
	/** Object State */
	public enum PostStatus {
	    POSTING,
	    ERROR
	}
	@Enumerated(EnumType.STRING)
	@Column(name="postStatus")
	protected PostStatus postStatus;
	public PostStatus getPostStatus() {
		return this.postStatus;
	}
	public String getPostStatusStr() {
		return this.postStatus.name();
	}
	public void setPostStatus(PostStatus newPostStatus) {
		this.postStatus = newPostStatus;
	}
	public void setPostStatusStr(String str) {
		this.postStatus = PostStatus.valueOf(str);
	}
	public Boolean isInPostingState() {
		return this.postStatus == PostStatus.POSTING;
	}
	public Boolean isInErrorState() {
		return this.postStatus == PostStatus.ERROR;
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
		this.postStatus = PostStatus.POSTING;
	}
	
	public TommyEnrichedJSON(Servizio s) throws IllegalArgumentException, JAXBException {
		this();
		this.setCodiceServizio(s.getCodiceServizio());
		this.setJsonServizio(s.toJSON());
	}
	
	public TommyEnrichedJSON (String codiceServizio, String jsonServizio) {
		this();
		this.setCodiceServizio(codiceServizio);
		this.setJsonServizio(jsonServizio);
	}
	public TommyEnrichedJSON (String codiceServizio, String jsonServizio, String postStatusStr) {
		this();
		this.setCodiceServizio(codiceServizio);
		this.setPostStatusStr(postStatusStr);		
		this.setJsonServizio(jsonServizio);
	}
	public TommyEnrichedJSON (String codiceServizio, String jsonServizio, PostStatus postStatus) {
		this();
		this.setCodiceServizio(codiceServizio);
		this.setPostStatus(postStatus);		
		this.setJsonServizio(jsonServizio);
	}
	
	public Servizio buildServizio() throws JAXBException {
		ServizioFactory sFact = new ServizioFactory();
		Servizio s = sFact.buildServizioUnmarshallJSON(jsonServizio);
		return s;
	}

	

	

	

	
	
}
