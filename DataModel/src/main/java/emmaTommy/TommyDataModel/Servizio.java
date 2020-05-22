package emmaTommy.TommyDataModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import emmaTommy.DataModel.DateAdapterYYMMDD;
import emmaTommy.DataModel.DateTimeAdapterHHmm;

@XmlRootElement(name = "servizio")
@XmlType(name = "Servizio")
@XmlAccessorType (XmlAccessType.FIELD)
public class Servizio extends TommyDataModel {
	
	@XmlTransient
	public static final String VALIDATE_OK = "SERVIZIO OK";	
	
	// "data" : "type:YYYYMMDD:required"
	@XmlElement(name = "data", required = true)	
	@XmlJavaTypeAdapter(DateAdapterYYMMDD.class)
	protected LocalDate missioneDate;
	public void setData(LocalDate missioneDate) {
		this.missioneDate = missioneDate;
	}
	public LocalDate getMissioneDate() {
		return this.missioneDate;
	}
	
	// "codice_servizio" : "type:String:required"
	@XmlElement(name = "codice_servizio", required = true)	
	protected String codice_servizio;
	public void setCodiceServizio(String codice_servizio) {
		this.codice_servizio = codice_servizio;
	}
	public String getCodiceServizio() {
		return this.codice_servizio;
	}
	
	// orario_inizio_servizio" : "type:HH:II:required"
	@XmlElement(name = "orario_inizio_servizio", required = true)	
	@XmlJavaTypeAdapter(DateTimeAdapterHHmm.class)
	protected LocalTime orario_inizio_servizio;
	public void setOrarioInizioServizio (LocalTime orario_inizio_servizio) {
		this.orario_inizio_servizio = orario_inizio_servizio;
	}
	protected LocalTime getOrarioInizioServizio() {
		return this.orario_inizio_servizio;
	}
	
	// orario_arrivo_posto" : "type:HH:II"
	@XmlElement(name = "orario_arrivo_posto", required = false)	
	@XmlJavaTypeAdapter(DateTimeAdapterHHmm.class)
	protected LocalTime orario_arrivo_posto;
	public void setOrarioArrivoPosto (LocalTime orario_arrivo_posto) {
		this.orario_arrivo_posto = orario_arrivo_posto;
	}
	public LocalTime getOrarioArrivoPosto() {
		return this.orario_arrivo_posto;
	}
	
	// orario_partenza_posto" : "type:HH:II"
	@XmlElement(name = "orario_partenza_posto", required = false)	
	@XmlJavaTypeAdapter(DateTimeAdapterHHmm.class)
	protected LocalTime orario_partenza_posto;
	public void setOrarioPartenzaPosto (LocalTime orario_partenza_posto) {
		this.orario_partenza_posto = orario_partenza_posto;
	}
	public LocalTime getOrarioPartenzaPosto() {
		return this.orario_partenza_posto;
	}
	
	// orario_arrivo_ospedale" : "type:HH:II"
	@XmlElement(name = "orario_arrivo_ospedale", required = false)
	@XmlJavaTypeAdapter(DateTimeAdapterHHmm.class)
	protected LocalTime orario_arrivo_ospedale; 
	public void setOrarioArrivoOspedale (LocalTime orario_arrivo_ospedale) {
		this.orario_arrivo_ospedale = orario_arrivo_ospedale;
	}
	public LocalTime getOrarioArrivoOspedale() {
		return this.orario_arrivo_ospedale;
	}
	
	// orario_partenza_ospedale" : "type:HH:II"
	@XmlElement(name = "orario_partenza_ospedale", required = false)
	@XmlJavaTypeAdapter(DateTimeAdapterHHmm.class)
	protected LocalTime orario_partenza_ospedale;
	public void setOrarioPartenzaOspedale (LocalTime orario_partenza_ospedale) {
		this.orario_partenza_ospedale = orario_partenza_ospedale;
	}
	public LocalTime getOrarioPartenzaOspedale() {
		return this.orario_partenza_ospedale;
	}
	
	// orario_fine_servizio" : "type:HH:II:required"
	@XmlElement(name = "orario_fine_servizio", required = true)	
	@XmlJavaTypeAdapter(DateTimeAdapterHHmm.class)
	protected LocalTime orario_fine_servizio;
	public void setOrarioFineServizio (LocalTime orario_fine_servizio) {
		this.orario_fine_servizio = orario_fine_servizio;
	}
	public LocalTime getOrarioFineServizio() {
		return this.orario_fine_servizio;
	}
	    	
	// tag_idintervento" : "type:String:required"
	@XmlElement(name = "tag_idintervento", required = true)	
	protected String tag_idintervento;
	public void setTagIdIntervento(String tag_idintervento) {
		this.tag_idintervento = tag_idintervento;
	}
	public String getTagIntervento() {
		return this.tag_idintervento;
	}
	
	// tag_idautomezzo" : "type:String:required"
	@XmlElement(name = "tag_idautomezzo", required = true)	
	protected String tag_idautomezzo;
	public void setTagIdAutomezzo(String tag_idautomezzo) {
		this.tag_idautomezzo = tag_idautomezzo;
	}
	public String getTagIdAutomezzo() {
		return this.tag_idautomezzo;
	}
	
	// km" : "type:Int:required"
	@XmlElement(name = "km", required = true)	
	protected int km;
	public void setKM(int km) {
		this.km = km;
	}
	public int getKM() {
		return this.km;
	}
	
	// luogo_partenza" : "type:String"
	@XmlElement(name = "luogo_partenza", required = false)	
	protected String luogo_partenza;
	public void setLuogoPartenza(String luogo_partenza) {
		this.luogo_partenza = luogo_partenza;
	}
	public String getLuogoPartenza() {
		return this.luogo_partenza;
	}
	
	// luogo_arrivo" : "type:String"
	@XmlElement(name = "luogo_arrivo", required = false)	
	protected String luogo_arrivo;
	public void setLuogoArrivo(String luogo_arrivo) {
		this.luogo_arrivo = luogo_arrivo;
	}
	public String getLuogoArrivo() {
		return this.luogo_arrivo;
	}
	
	// Assistiti
	@XmlElement(name = "assistiti", required = true)	
	protected ArrayList<Assistito> assistiti;
	public void setAssistiti(ArrayList<Assistito> arrayList) {
		this.assistiti = arrayList;
	}
	public ArrayList<Assistito> getAssistiti() {
		return this.assistiti;
	}
	
	// Squadra
	@XmlElement(name = "squadra", required = true)	
	protected ArrayList<Membro> squadra;
	public void setSquadra(ArrayList<Membro> arrayList) {
		this.squadra = arrayList;
	}
	public ArrayList<Membro> getSquadra() {
		return this.squadra;
	}
	
	
	// note" : "type:String" */	
	@XmlElement(name = "note", required = false)	
	protected String note;
	public void setNote(String note) {
		this.note = note;
	}
	public String getNote() {
		return this.note;
	}
	
		
	/** Empty Constructor */
	public Servizio() {
		super();
	}
	
	
	@Override
	@XmlTransient
	public Boolean validateObject() {
		ArrayList<String> errorMsgList = this.validateForStoring();
		if (errorMsgList == null) {
			return false;
		}
		if (errorMsgList.isEmpty() ) {
			return true;
		} else {
			return false;
		}
	}
	
	@XmlTransient
	public ArrayList<String> validateForStoring() {
		ArrayList<String> errorMsgList = new ArrayList<String>();
		return errorMsgList;
	}
	
	

}
