package emmaTommy.TommyDataModel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.TreeMap;

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
	public LocalTime getOrarioInizioServizio() {
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assistiti == null) ? 0 : assistiti.hashCode());
		result = prime * result + ((codice_servizio == null) ? 0 : codice_servizio.hashCode());
		result = prime * result + km;
		result = prime * result + ((luogo_arrivo == null) ? 0 : luogo_arrivo.hashCode());
		result = prime * result + ((luogo_partenza == null) ? 0 : luogo_partenza.hashCode());
		result = prime * result + ((missioneDate == null) ? 0 : missioneDate.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + ((orario_arrivo_ospedale == null) ? 0 : orario_arrivo_ospedale.hashCode());
		result = prime * result + ((orario_arrivo_posto == null) ? 0 : orario_arrivo_posto.hashCode());
		result = prime * result + ((orario_fine_servizio == null) ? 0 : orario_fine_servizio.hashCode());
		result = prime * result + ((orario_inizio_servizio == null) ? 0 : orario_inizio_servizio.hashCode());
		result = prime * result + ((orario_partenza_ospedale == null) ? 0 : orario_partenza_ospedale.hashCode());
		result = prime * result + ((orario_partenza_posto == null) ? 0 : orario_partenza_posto.hashCode());
		result = prime * result + ((squadra == null) ? 0 : squadra.hashCode());
		result = prime * result + ((tag_idautomezzo == null) ? 0 : tag_idautomezzo.hashCode());
		result = prime * result + ((tag_idintervento == null) ? 0 : tag_idintervento.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Servizio other = (Servizio) obj;
		if (assistiti == null) {
			if (other.assistiti != null)
				return false;
		} else if (assistiti.size() != other.assistiti.size()) {
			return false;
		} else {
			for (int assistitoNum = 0; assistitoNum < assistiti.size(); assistitoNum++) {
				Assistito assistito = assistiti.get(assistitoNum);
				Assistito assistitoOther = other.assistiti.get(assistitoNum);
				if (assistito == null) {
					if (assistitoOther!= null)
						return false;
				} else {
					if (!assistito.equals(assistitoOther))
						return false;
				}
			}
		}
		if (codice_servizio == null) {
			if (other.codice_servizio != null)
				return false;
		} else if (!codice_servizio.equals(other.codice_servizio))
			return false;
		if (km != other.km)
			return false;
		if (luogo_arrivo == null) {
			if (other.luogo_arrivo != null)
				return false;
		} else if (!luogo_arrivo.equals(other.luogo_arrivo))
			return false;
		if (luogo_partenza == null) {
			if (other.luogo_partenza != null)
				return false;
		} else if (!luogo_partenza.equals(other.luogo_partenza))
			return false;
		if (missioneDate == null) {
			if (other.missioneDate != null)
				return false;
		} else if (!missioneDate.equals(other.missioneDate))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (orario_arrivo_ospedale == null) {
			if (other.orario_arrivo_ospedale != null)
				return false;
		} else if (!orario_arrivo_ospedale.equals(other.orario_arrivo_ospedale))
			return false;
		if (orario_arrivo_posto == null) {
			if (other.orario_arrivo_posto != null)
				return false;
		} else if (!orario_arrivo_posto.equals(other.orario_arrivo_posto))
			return false;
		if (orario_fine_servizio == null) {
			if (other.orario_fine_servizio != null)
				return false;
		} else if (!orario_fine_servizio.equals(other.orario_fine_servizio))
			return false;
		if (orario_inizio_servizio == null) {
			if (other.orario_inizio_servizio != null)
				return false;
		} else if (!orario_inizio_servizio.equals(other.orario_inizio_servizio))
			return false;
		if (orario_partenza_ospedale == null) {
			if (other.orario_partenza_ospedale != null)
				return false;
		} else if (!orario_partenza_ospedale.equals(other.orario_partenza_ospedale))
			return false;
		if (orario_partenza_posto == null) {
			if (other.orario_partenza_posto != null)
				return false;
		} else if (!orario_partenza_posto.equals(other.orario_partenza_posto))
			return false;
		if (squadra == null) {
			if (other.squadra != null)
				return false;
		} else if (squadra.size() != other.squadra.size()) {
			return false;
		} else {
			for (int membroNum = 0; membroNum < squadra.size(); membroNum++) {
				Membro membro = squadra.get(membroNum);
				Membro membroOther = other.squadra.get(membroNum);
				if (membro == null) {
					if (membroOther != null)
						return false;
				} else {
					if (!membro.equals(membroOther)) 
						return false;
				}
			}
		}
		if (tag_idautomezzo == null) {
			if (other.tag_idautomezzo != null)
				return false;
		} else if (!tag_idautomezzo.equals(other.tag_idautomezzo))
			return false;
		if (tag_idintervento == null) {
			if (other.tag_idintervento != null)
				return false;
		} else if (!tag_idintervento.equals(other.tag_idintervento))
			return false;
		return true;
	}
	
	

}
