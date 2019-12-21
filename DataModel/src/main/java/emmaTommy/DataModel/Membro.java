package emmaTommy.DataModel;

import java.util.ArrayList;

import javax.xml.bind.annotation.*;

import org.apache.logging.log4j.LogManager;

@XmlRootElement(name = "membro")
@XmlAccessorType(XmlAccessType.FIELD)
public class Membro extends DataFormatClass {
    	
	public Membro() {
		super();
	}
	public Membro(String nome, String cognome, String qualifica) {
		super();
		this.setCognome(cognome);
		this.setNome(nome);
		this.setQualifica(qualifica);
		this.validateObject();
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
		else if (this.type.isBlank()) {
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
			logger.error(errorMsg);
		}
		
		// Name Type
		if (this.nome == null) {
			this.validState = false;
			errorMsg += "nome was NULL";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		else if (this.nome.isEmpty()) {
			this.validState = false;
			errorMsg += "nome was Empty";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		else if (this.nome.isBlank()) {
			this.validState = false;
			errorMsg += "nome was Blanck";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		
		// Cognome Type
		if (this.cognome == null) {
			this.validState = false;
			errorMsg += "cognome was NULL";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		else if (this.cognome.isEmpty()) {
			this.validState = false;
			errorMsg += "cognome was Empty";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		else if (this.cognome.isBlank()) {
			this.validState = false;
			errorMsg += "cognome was Blanck";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		
		// Qualifica
		if (this.qualifica == null) {
			this.validState = false;
			errorMsg += "qualifica was NULL";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		else if (this.qualifica.isEmpty()) {
			this.validState = false;
			errorMsg += "qualifica was Empty";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		else if (this.qualifica.isBlank()) {
			this.validState = false;
			errorMsg += "qualifica was Blanck";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		else if (!DataModelEnums.acceptedQualifiche.contains(qualifica)) {
			this.validState = false;
			errorMsg += qualifica + "isn't in the accepted list of qualifiche (New One?)";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		
		// Return validState
		return this.validState;
	}
	
	/** type Attribute */
	@XmlAttribute(name = "type")
	protected String type = this.getClass().getSimpleName();	
	public void setType(String type) {
		this.type = type;	
	}
	public String getType() {
		return this.type;
	}
	
	
	/** Cognome del Membro dell'Equipaggio */
	@XmlElement(name = "me-ds-cognome", required = true, nillable = false)	
	protected String cognome;	
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getCognome() {
		return this.cognome;
	}
	
	/** Nome del Membro dell'Equipaggio */
	@XmlElement(name = "me-ds-nome", required = true, nillable = false)	
	protected String nome;	
	public void setNome(String nome) {
		this.nome = nome;	
	}
	public String getNome() {
		return this.nome;
	}
	
	/** Qualifica del Membro dell'Equipaggio */	
	@XmlElement(name = "me-ds-tp-equip", required = true, nillable = false)	
	protected String qualifica;	
	public void setQualifica(String qualifica) {
		this.qualifica = qualifica;
	}
	public String getQualifica() {
		return this.qualifica;
	}
	
	@Override
	public String toString() {
		return "Membro [cognome=" + cognome + ", nome=" + nome + ", qualifica=" + qualifica + ", type=" + type + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cognome == null) ? 0 : cognome.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((qualifica == null) ? 0 : qualifica.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (cognome == null) {
			if (other.cognome != null)
				return false;
		} else if (!cognome.equals(other.cognome))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (qualifica == null) {
			if (other.qualifica != null)
				return false;
		} else if (!qualifica.equals(other.qualifica))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	
	

}
