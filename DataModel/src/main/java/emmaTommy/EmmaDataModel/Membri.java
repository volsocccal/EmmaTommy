package emmaTommy.EmmaDataModel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

import emmaTommy.EmmaDataModel.Membro;
 

@XmlRootElement(name = "membri")
@XmlType(name = "Membri")
@XmlAccessorType (XmlAccessType.FIELD)
@XmlDiscriminatorNode("@@type")
public class Membri extends EmmaDataModel
{
	
	/** Empty Constructor */
	public Membri() {
		super(); 
		this.membri = new ArrayList<Membro>();
	}
	
	public Membri(ArrayList<Membro> membri) {
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
		if (this.type.compareTo(this.getClass().getSimpleName()) != 0) {
			this.type = this.getClass().getSimpleName();
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
					this.errorList.addAll(m.getErrorList());
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
		Membri other = (Membri) obj;
		if (membri == null) {
			if (other.getMembri() != null)
				return false;
		} else if (!membri.equals(other.getMembri()))
			return false;
		return true;
	}
    
    
    
}