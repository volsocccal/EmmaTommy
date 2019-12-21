package emmaTommy.DataModel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import emmaTommy.DataModel.Missione;
 
@XmlRootElement(name = "missioni")
@XmlAccessorType (XmlAccessType.FIELD)
public class Missioni extends DataFormatClass {
	
	/** Empty Constructor */
	public Missioni() {
		super();
		this.missioni = new ArrayList<Missione>();
	}
	
	/** validateObject
	 * type: not null, not empty, not blanck, equals "array"
	 * missioni list: not null, not empty, every element is not in errorStatus
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
		if (this.type.compareTo("array") != 0) {
			this.validState = false;
			errorMsg += "type wasnt array";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
				
		// Check Missioni List
		if (this.missioni == null) {
			this.validState = false;
			errorMsg += "Missioni list was NULL";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		if (this.missioni.size() == 0) {
			this.validState = false;
			errorMsg += "Missioni list was EMPTY";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		} else {
			for (Missione m : this.missioni) {
				if (m == null) {
					this.validState = false;
					errorMsg += "Missioni list has a NULL element";
					this.errorList.add(errorMsg);
					logger.warn(errorMsg);
				} else {
					Boolean elValidState = m.validateObject();
					if (!elValidState) {
						this.validState = false;
						this.errorList.addAll(m.getErrorList());
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
	
	/** Missioni Annotated List */
    @XmlElement(name = "missione")
    protected List<Missione> missioni = null; 
    public List<Missione> getMissioni() {
        return this.missioni;
    } 
    public void setMissioni(List<Missione> missioni) {
    	if (missioni == null) {
    		logger.error("::setMissioni(): " + "missioni list was null");
    		throw new NullPointerException("::setMissioni(): null missioni list");
    	}
        this.missioni = missioni;
        logger.trace("::setMissioni(): " + "added missioni list of size " + missioni.size());
    }
    
    @Override
	public String toString() {
		String str = "Missioni [";
		for (Missione m: this.missioni) {
			str += m.toString();
		}
		str += "]";
		return str;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((missioni == null) ? 0 : missioni.hashCode());
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
		Missioni other = (Missioni) obj;
		if (missioni == null) {
			if (other.missioni != null)
				return false;
		} else if (!missioni.equals(other.missioni))
			return false;
		return true;
	}
    
    
    
}
