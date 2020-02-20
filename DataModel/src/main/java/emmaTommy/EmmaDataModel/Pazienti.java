package emmaTommy.EmmaDataModel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

import emmaTommy.EmmaDataModel.Paziente;
 
@XmlRootElement(name = "pazienti")
@XmlType(name = "Pazienti")
@XmlAccessorType (XmlAccessType.FIELD)
@XmlDiscriminatorNode("@@type")
public class Pazienti extends EmmaDataModel {
	
	/** Empty Constructor */
	public Pazienti() {
		super();
		this.pazienti = new ArrayList<Paziente>();
	}
	
	/** validateObject
	 * type: not null, not empty, not blanck, equals "array"
	 * Pazienti list: not null, not empty, every element is not in errorStatus
	 * 
	 * @return true if the object is valid, false otherwise
	 */
	@XmlTransient
	public Boolean validateObject() {
		String errorMsg = this.getClass().getSimpleName() + ": ";
		
		// Check Type
		errorMsg = this.getClass().getSimpleName() + ": ";
		if (this.type == null) {
			this.validState = false;
			errorMsg += "type was NULL";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		if (this.type.isBlank()) {
			this.validState = false;
			errorMsg += "type was Empty";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		if (this.type.isBlank()) {
			this.validState = false;
			errorMsg += "type was Blanck";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		if (this.type.compareTo(this.getClass().getSimpleName()) != 0) {
			this.type = this.getClass().getSimpleName();
		}
				
		// Check Pazienti List
		errorMsg = this.getClass().getSimpleName() + ": ";
		errorMsg = this.getClass().getSimpleName() + ": ";
		if (this.pazienti == null) {
			this.validState = false;
			errorMsg += "Pazienti list was NULL";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		if (this.pazienti.size() == 0) {
			this.validState = false;
			errorMsg += "Pazienti list was EMPTY";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		} else {
			for (Paziente p : this.pazienti) {
				if (p == null) {
					this.validState = false;
					errorMsg += "Pazienti list has a NULL element";
					this.errorList.add(errorMsg);
					logger.warn(errorMsg);
				} else {
					Boolean elValidState = p.validateObject();
					if (!elValidState) {
						this.validState = false;
						this.errorList.addAll(p.getErrorList());
					}
				}
			}
		}
		
		// Return validState
		return this.validState;
	}
		
	/** type Attribute */
	@XmlAttribute(name = "type")
	protected String type = "array";	
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return this.type;
	}
	
	/** Pazienti Annotated List */
    @XmlElement(name = "paziente")
    protected List<Paziente> pazienti = null; 
    public List<Paziente> getPazienti() {
        return this.pazienti;
    } 
    public void setPazienti(List<Paziente> pazienti) {
    	if (pazienti == null) {
    		logger.error("::setPazienti(): " + "pazienti list was null");
    		throw new NullPointerException("::setPazienti(): null pazienti list");
    	}
    	this.pazienti = pazienti;
        logger.trace("::setPazienti(): " + "added pazienti list of size " + pazienti.size());       
    }
    
	@Override
	public String toString() {
		String str = "Pazienti [";
		for (Paziente p: this.pazienti) {
			str += p.toString();
		}
		str += "]";
		return str;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pazienti == null) ? 0 : pazienti.hashCode());
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
		Pazienti other = (Pazienti) obj;
		if (pazienti == null) {
			if (other.getPazienti() != null)
				return false;
		} else if (!pazienti.equals(other.getPazienti()))
			return false;
		return true;
	}
    
    
    
}