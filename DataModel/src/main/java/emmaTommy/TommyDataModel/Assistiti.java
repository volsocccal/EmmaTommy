package emmaTommy.TommyDataModel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
 
@XmlRootElement(name = "Assistiti")
@XmlAccessorType (XmlAccessType.FIELD)
public class Assistiti extends TommyDataModel
{
	
	/** Empty Constructor */
	public Assistiti() {
		super(); 
		this.assistiti = new ArrayList<Assistito>();
	}
	
	public Assistiti(ArrayList<Assistito> assistiti) {
		super();
		this.setAssistiti(assistiti);
		this.validateObject();
	}
	
	/** validateObject
	 * type: not null, not empty, not blanck, equals "array"
	 * assistiti list: not null, not empty, every element is not in errorStatus
	 * 
	 * @return true if the object is valid, false otherwise
	 */
	@XmlTransient
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
				
		// Check assistiti List
		if (this.assistiti == null) {
			this.validState = false;
			errorMsg += "Assistiti list was NULL";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		if (this.assistiti.size() == 0) {
			this.validState = false;
			errorMsg += "Assistiti list was EMPTY";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		} else {
			for (Assistito ass : this.assistiti) {
				if (ass == null) {
					this.validState = false;
					errorMsg += "Assistiti list has a NULL element";
					this.errorList.add(errorMsg);
					logger.warn(errorMsg);
				} else {
					Boolean elValidState = ass.validateObject();
					if (!elValidState) {
						this.validState = false;
						this.errorList.addAll(ass.getErrorList());
					}
				}
			}
		}
		
		// Return validState
		return this.validState;
	}
		
	
	
	/** type Attribute */
	@XmlAttribute(name = "type")
	@XmlTransient()
	protected String type = "array";	
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return this.type;
	}
	
	/** Assistiti Annotated List */  
	@XmlElement(name = "assistiti")
    protected List<Assistito> assistiti = null;     
    public List<Assistito> getAssistiti() {
        return this.assistiti;
    } 
    public void setAssistiti(List<Assistito> assistiti) {
    	this.assistiti = assistiti;
        logger.trace("::setAssistiti(): " + "added membri list of size " + assistiti.size());        
    }
    
    @Override
	public String toString() {
		String str = "Assistiti [";
		for (Assistito ass: this.assistiti) {
			str += ass.toString();
		}
		str += "]";
		return str;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assistiti == null) ? 0 : assistiti.hashCode());
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
		Assistiti other = (Assistiti) obj;
		if (assistiti == null) {
			if (other.getAssistiti() != null)
				return false;
		} else if (!assistiti.equals(other.getAssistiti()))
			return false;
		return true;
	}
    
    
    
}