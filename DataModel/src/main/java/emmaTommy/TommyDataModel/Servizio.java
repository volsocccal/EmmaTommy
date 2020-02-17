package emmaTommy.TommyDataModel;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

@XmlRootElement(name = "servizio")
@XmlType(name = "Servizio")
@XmlAccessorType (XmlAccessType.FIELD)
public class Servizio extends TommyDataModel {
	
	public static final String VALIDATE_OK = "SERVIZIO OK";	
	
	// "data" : "type:YYYYMMDD:required"
	@XmlElement(name = "data", required = true)	
	protected Date data;
	public void setData(Date data) {
		this.data = data;
	}
	public Date getData() {
		return this.data;
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
	protected Date orario_inizio_servizio;
	public void setOrarioInizioServizio (Date orario_inizio_servizio) {
		this.orario_inizio_servizio = orario_inizio_servizio;
	}
	protected Date getOrarioInizioServizio() {
		return this.orario_inizio_servizio;
	}
	
	// orario_arrivo_posto" : "type:HH:II"
	@XmlElement(name = "orario_arrivo_posto", required = false)	
	protected Date orario_arrivo_posto;
	public void setOrarioArrivoPosto (Date orario_arrivo_posto) {
		this.orario_arrivo_posto = orario_arrivo_posto;
	}
	public Date getOrarioArrivoPosto() {
		return this.orario_arrivo_posto;
	}
	
	// orario_partenza_posto" : "type:HH:II"
	@XmlElement(name = "orario_partenza_posto", required = false)	
	protected Date orario_partenza_posto;
	public void setOrarioPartenzaPosto (Date orario_partenza_posto) {
		this.orario_partenza_posto = orario_partenza_posto;
	}
	public Date getOrarioPartenzaPosto() {
		return this.orario_partenza_posto;
	}
	
	// orario_arrivo_ospedale" : "type:HH:II"
	@XmlElement(name = "orario_arrivo_ospedale", required = false)	
	protected Date orario_arrivo_ospedale; 
	public void setOrarioArrivoOspedale (Date orario_arrivo_ospedale) {
		this.orario_arrivo_ospedale = orario_arrivo_ospedale;
	}
	public Date getOrarioArrivoOspedale() {
		return this.orario_arrivo_ospedale;
	}
	
	// orario_partenza_ospedale" : "type:HH:II"
	@XmlElement(name = "orario_partenza_ospedale", required = false)	
	protected Date orario_partenza_ospedale;
	public void setOrarioPartenzaOspedale (Date orario_partenza_ospedale) {
		this.orario_partenza_ospedale = orario_arrivo_ospedale;
	}
	public Date getOrarioPartenzaOspedale() {
		return this.orario_partenza_ospedale;
	}
	
	// orario_fine_servizio" : "type:HH:II:required"
	@XmlElement(name = "orario_fine_servizio", required = true)	
	protected Date orario_fine_servizio;
	public void setOrarioFineServizio (Date orario_fine_servizio) {
		this.orario_fine_servizio = orario_fine_servizio;
	}
	public Date getOrarioFineServizio() {
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
	protected Assistiti assistiti;
	public void setAssistiti(Assistiti assistiti) {
		this.assistiti = assistiti;
	}
	public Assistiti getAssistiti() {
		return this.assistiti;
	}
	
	// Squadra
	@XmlElement(name = "squadra", required = true)	
	protected Squadra squadra;
	public void setSquadra(Squadra squadra) {
		this.squadra = squadra;
	}
	public Squadra getSquadra() {
		return this.squadra;
	}
	
	
		
	/** Empty Constructor */
	public Servizio() {
		super();
	}
	
	
	@Override
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
	
	public ArrayList<String> validateForStoring() {
		ArrayList<String> errorMsgList = new ArrayList<String>();
		return errorMsgList;
	}
	
	

}
