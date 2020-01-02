package emmaTommy.DataModel;

import java.util.Date;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "tratta")
@XmlAccessorType(XmlAccessType.FIELD)
public class Tratta extends DataFormatClass {
	
	public Tratta() {
		super();
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
		this.setId(id);
		this.setDataPartenza(dataPartenza);
		this.setDataArrivo(dataArrivo);
		this.setDestinazione(destinazione);
	}
	
	/** validateObject
	 * type: not null, not empty, not blanck, equals to the simple class name
	 * nome: not null, not empty, not blanck
	 * cognome: not null, not empty, not blanck
	 * qualifica: not null, not empty, not blanck, part of the Qualifiche.accepted_qualifiche List
	 * 
	 * @return true if the object is valid, false otherwise
	 */
	public Boolean validateObject() {
		String errorMsg = this.getClass().getSimpleName() + ": ";
		
		// Check Type
		if (this.type == null) {
			this.validState = false;
			errorMsg += "type was NULL";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		else if (this.type.isEmpty()) {
			this.validState = false;
			errorMsg += "type was Empty";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		else if (this.type.isBlank()) {
			this.validState = false;
			errorMsg += "type was Blanck";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		else if (this.type.compareTo(this.getClass().getSimpleName()) != 0) {
			this.validState = false;
			errorMsg += "type was diffent from ClassName";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		
		// Check Id
		if (this.id == 0) {
			this.validState = false;
			errorMsg += "id was zero";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		} else if (this.id < 0) {
			this.validState = false;
			errorMsg += "id was negative";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		
		// Check DateTimes
		if (this.dataArrivo != null) {
			if (this.dataPartenza != null) {
				if (this.dataPartenza.after(this.dataArrivo)) { // Check timeline
					this.validState = false;
					errorMsg += "partenza was afer arrivo";
					this.errorList.add(errorMsg);
					logger.warn(errorMsg);
				}
			} else {
				this.validState = false;
				errorMsg += "partenza was null but arrivo was an accepted value";
				this.errorList.add(errorMsg);
				logger.warn(errorMsg);
			}
		}
		
		// Return validState
		return this.validState;
	}
	
	/** type Attribute */
	@XmlAttribute(name = "type")
	protected String type = this.getClass().getSimpleName();	
	public void setType(String type) {
		this.type = type;
		logger.trace("::setType(): " + type);	
	}
	public String getType() {
		return this.type;
	}
	
	/** Codice ID Della Tratta */	
	@XmlElement(name = "tr-id-tratta", required = true, nillable=false)	
	protected int id;	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return this.id;
	}
	
	/** Data di Partenza */
	@XmlElement(name = "tr-dt-partenza", required = true)	
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
	protected Date dataPartenza;	
	public void setDataPartenza(Date dataPartenza) {
		this.dataPartenza = dataPartenza;
	}
	public Date getDataPartenza() {
		return this.dataPartenza; 
	}
	
	/** Data di Arrivo */	
	@XmlElement(name = "tr-dt-arrivo", required = true)	
	@XmlJavaTypeAdapter(DateTimeAdapter.class)
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
			if (other.getDataArrivo() != null)
				return false;
		} else if (!dataArrivo.equals(other.getDataArrivo()))
			return false;
		if (dataPartenza == null) {
			if (other.getDataPartenza() != null)
				return false;
		} else if (!dataPartenza.equals(other.getDataPartenza()))
			return false;
		if (destinazione == null) {
			if (other.getDestinazione() != null)
				return false;
		} else if (!destinazione.equals(other.getDestinazione()))
			return false;
		if (id != other.getId())
			return false;
		return true;
	}
	
}
