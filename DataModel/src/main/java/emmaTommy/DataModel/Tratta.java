package emmaTommy.DataModel;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "tratta")
@XmlAccessorType(XmlAccessType.FIELD)
public class Tratta extends toDataFormatClass {
	
	@XmlTransient
    protected Boolean OkStatus;
    public void setOkStatus(Boolean status) {
    	this.OkStatus = status;
    }
    protected Boolean getOkStatus() {
    	return this.OkStatus;
    }
    
    @XmlTransient
    protected ArrayList<String> errorList;
    public ArrayList<String> getErrorList() {
    	return this.getErrorList();
    }    

	public Tratta() {
		super();
		this.OkStatus = true; 
		this.errorList = new ArrayList<String>(); 
	}
	
	public Tratta(int id, Date dataPartenza) {
		this(id, dataPartenza, null, null);
	}
	
	public Tratta(int id, Date dataPartenza, Date dataArrivo) {
		this(id, dataPartenza, dataArrivo, null);
	}
	
	public Tratta(int id, Date dataPartenza, String destinazione) {
		this(id, dataPartenza, null, destinazione);
	}
	
	public Tratta(int id, Date dataPartenza, Date dataArrivo, String destinazione) {
		super();
		this.OkStatus = true; 
		this.errorList = new ArrayList<String>(); 
		this.setId(id);
		this.setDataPartenza(dataPartenza);
		this.setDataPartenza(dataPartenza);
		this.setDestinazione(destinazione);
	}
	
	/** Codice ID Della Tratta */
	@XmlElement(name = "tr-id-tratta", required = true)	
	protected int id;
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return this.id;
	}
	
	/** Data di Partenza */
	@XmlElement(name = "tr-dt-partenza", required = true)	
	protected Date dataPartenza;
	public void setDataPartenza(Date dataPartenza) {
		this.dataPartenza = dataPartenza;
	}
	public Date getDataPartenza() {
		return this.dataPartenza; 
	}
	
	/** Data di Arrivo */
	@XmlElement(name = "tr-dt-arrivo", required = true)	
	protected Date dataArrivo;
	public void setDataArrivo(Date dataArrivo) {
		this.dataArrivo = dataArrivo;
	}
	public Date getDataArrivo() {
		return this.dataArrivo; 
	}
	
	/** Luogo di Destinazione */
	@XmlElement(name = "tr-ds-lg-dest", required = false)	
	protected String destinazione;
	public void setDestinazione(String destinazione) {
		this.destinazione = destinazione;
	}
	public String getDestinazione() {
		return this.destinazione; 
	}
	
	@Override
	public String toString() {
		return "Tratta [id=" + id + ", dataPartenza=" + dataPartenza.toString() + ", dataArrivo=" + dataArrivo.toString() + ", destinazione="
				+ destinazione + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataArrivo == null) ? 0 : dataArrivo.hashCode());
		result = prime * result + ((dataPartenza == null) ? 0 : dataPartenza.hashCode());
		result = prime * result + ((destinazione == null) ? 0 : destinazione.hashCode());
		result = prime * result + id;
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
		Tratta other = (Tratta) obj;
		if (dataArrivo == null) {
			if (other.dataArrivo != null)
				return false;
		} else if (!dataArrivo.equals(other.dataArrivo))
			return false;
		if (dataPartenza == null) {
			if (other.dataPartenza != null)
				return false;
		} else if (!dataPartenza.equals(other.dataPartenza))
			return false;
		if (destinazione == null) {
			if (other.destinazione != null)
				return false;
		} else if (!destinazione.equals(other.destinazione))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	
}
