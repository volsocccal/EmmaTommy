package emmaTommy.DataModel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import emmaTommy.DataModel.Tratta;
 
@XmlRootElement(name = "tratte")
@XmlAccessorType (XmlAccessType.FIELD)
public class Tratte  extends DataFormatClass {
	
	/** Empty Constructor */
	public Tratte() {
		super();
		this.tratte = new ArrayList<Tratta>();
	}
	
	/** validateObject
	 * type: not null, not empty, not blanck, equals "array"
	 * tratte list: not null, not empty, every element is not in errorStatus
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
				
		// Check Tratte List
		if (this.tratte == null) {
			this.validState = false;
			errorMsg += "Tratte list was NULL";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		if (this.tratte.size() == 0) {
			this.validState = false;
			errorMsg += "Tratte list was EMPTY";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		} else {
			ArrayList<Integer> ids = new ArrayList<Integer>();
			for (Tratta t : this.tratte) {
				if (t == null) {
					this.validState = false;
					errorMsg += "Tratte list has a NULL element";
					this.errorList.add(errorMsg);
					logger.warn(errorMsg);
				} else {
					Boolean elValidState = t.validateObject();
					if (!elValidState) {
						this.validState = false;
						this.errorList.addAll(t.getErrorList());
					}
					if (ids.contains(t.getId())) {
						this.validState = false;
						errorMsg += "Tratta list has a multiple id " + t.getId() + " element";
						this.errorList.add(errorMsg);
						logger.warn(errorMsg);
					} else {
						ids.add(t.getId());
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
	
	/** Tratte Annotated List */
    @XmlElement(name = "tratta")
    protected List<Tratta> tratte = null; 
    public List<Tratta> getTratte() {
        return this.tratte;
    } 
    public void setTratte(List<Tratta> tratte) {
    	if (tratte == null) {
    		logger.error("::setTratte(): " + "tratte list was null");
    		throw new NullPointerException("::setTratte(): null tratte list");
    	}
    	this.tratte = tratte;
        logger.trace("::setTratte(): " + "added tratte list of size " + tratte.size());       
    }
    
    @Override
	public String toString() {
		String str = "Tratte [";
		for (Tratta t: this.tratte) {
			str += t.toString();
		}
		str += "]";
		return str;
	}
    
}