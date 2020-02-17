package emmaTommy.TommyDataModel;

import javax.xml.bind.annotation.*;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

@XmlRootElement(name = "membro")
@XmlType(name = "Membro")
@XmlAccessorType (XmlAccessType.FIELD)
@XmlDiscriminatorNode("@@type")
public class Membro extends TommyDataModel {
    	
	public Membro() {
		super();
	}
	public Membro(String tag_idanagrafica, String tag_idqualifica) {
		super();
		this.setTagIdAnagrafica(tag_idanagrafica);
		this.setTagIdQualifica(tag_idqualifica);
		this.validateObject();
	}
	
	/** validateObject
	 * tag_idanagrafica: not null, not empty, not blanck
	 * tag_idqualifica: not null, not empty, not blanck, part of the Qualifiche.accepted_qualifiche List
	 * 
	 * @return true if the object is valid, false otherwise
	 */
	public Boolean validateObject() {
		String errorMsg = this.getClass().getSimpleName() + ": ";
		
		// tag_idanagrafica Type
		if (this.tag_idanagrafica == null) {
			this.validState = false;
			errorMsg += "tag_idanagrafica was NULL";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		else if (this.tag_idanagrafica.isEmpty()) {
			this.validState = false;
			errorMsg += "tag_idanagrafica was Empty";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		else if (this.tag_idanagrafica.isBlank()) {
			this.validState = false;
			errorMsg += "nome was Blanck";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		
		// Qualifica
		if (this.tag_idqualifica == null) {
			this.validState = false;
			errorMsg += "tag_idqualifica was NULL";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		else if (this.tag_idqualifica.isEmpty()) {
			this.validState = false;
			errorMsg += "tag_idqualifica was Empty";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		else if (this.tag_idqualifica.isBlank()) {
			this.validState = false;
			errorMsg += "tag_idqualifica was Blanck";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		else if (!TommyDataModelEnums.acceptedQualifiche.contains(tag_idqualifica)) {
			this.validState = false;
			errorMsg += tag_idqualifica + "isn't in the accepted list of qualifiche (New One?)";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		
		// Return validState
		return this.validState;
	}


	/** tag_idanagrafica" : "type:String:required" */
	@XmlElement(name = "tag_idanagrafica", required = true, nillable = false)	
	protected String tag_idanagrafica;	
	public void setTagIdAnagrafica(String tag_idanagrafica) {
		this.tag_idanagrafica = tag_idanagrafica;	
	}
	public String getTagIdAnagrafica() {
		return this.tag_idanagrafica;
	}
	
	/** tag_idqualifica : "type:String:required" */	
	@XmlElement(name = "tag_idqualifica", required = true, nillable = false)	
	protected String tag_idqualifica;	
	public void setTagIdQualifica(String tag_idqualifica) {
		this.tag_idqualifica = tag_idqualifica;
	}
	public String getTagIdQualifica() {
		return this.tag_idqualifica;
	}
	
	@Override
	public String toString() {
		return "Membro [" + "tag_idanagrafica=" + tag_idanagrafica + ", tag_idqualifica=" + tag_idqualifica + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tag_idanagrafica == null) ? 0 : tag_idanagrafica.hashCode());
		result = prime * result + ((tag_idqualifica == null) ? 0 : tag_idqualifica.hashCode());
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
		Membro other = (Membro) obj;
		if (tag_idanagrafica == null) {
			if (other.getTagIdQualifica() != null)
				return false;
		} else if (!tag_idanagrafica.equals(other.getTagIdAnagrafica()))
			return false;
		if (tag_idqualifica == null) {
			if (other.getTagIdQualifica() != null)
				return false;
		} else if (!tag_idqualifica.equals(other.getTagIdQualifica()))
			return false;
		return true;
	}
	
	
	

}
