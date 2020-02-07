package emmaTommy.TommyDataModel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import emmaTommy.EmmaDataModel.Membro;
 
@XmlRootElement(name = "squadra")
@XmlAccessorType (XmlAccessType.FIELD)
public class Squadra extends TommyDataModel
{
	
	/** Empty Constructor */
	public Squadra() {
		super(); 
		this.membri = new ArrayList<Membro>();
	}
	
	public Squadra(ArrayList<Membro> membri) {
		super();
		this.setMembri(membri);
		this.validateObject();
	}
	
	/** validateObject
	 * type: not null, not empty, not blanck, equals "array"
	 * membri list: not null, not empty, every element is not in errorStatus
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
				
		// Check Membri List
		if (this.membri == null) {
			this.validState = false;
			errorMsg += "Membri list was NULL";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		if (this.membri.size() == 0) {
			this.validState = false;
			errorMsg += "Membri list was EMPTY";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		} else {
			for (Membro m : this.membri) {
				if (m == null) {
					this.validState = false;
					errorMsg += "Membri list has a NULL element";
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
	
	/** Membri Annotated List */  
	@XmlElement(name = "membro")
    protected List<Membro> membri = null;     
    public List<Membro> getMembri() {
        return this.membri;
    } 
    public void setMembri(List<Membro> membri) {
    	this.membri = membri;
        logger.trace("::setMembri(): " + "added membri list of size " + membri.size());        
    }
    
    @Override
	public String toString() {
		String str = "Membri [";
		for (Membro m: this.membri) {
			str += m.toString();
		}
		str += "]";
		return str;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((membri == null) ? 0 : membri.hashCode());
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
		Squadra other = (Squadra) obj;
		if (membri == null) {
			if (other.getMembri() != null)
				return false;
		} else if (!membri.equals(other.getMembri()))
			return false;
		return true;
	}
    
    
    
}