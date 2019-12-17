package emmaTommy.DataModel;

import java.util.ArrayList;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "membro")
@XmlAccessorType(XmlAccessType.FIELD)
public class Membro extends toDataFormatClass {
	
	static String AUTISTA = "AUTISTA";
	static String DAE = "OPERATORE D.A.E.";
	static String SOCCORRITORE = "SOCCORRITORE";
	static String CAPOSERVIZIO = "CAPO SERVIZIO";
	
	@XmlTransient
	private ArrayList<String> acceptedQualifiche = new ArrayList<String>() { 
		private static final long serialVersionUID = 1L;
		{ 
            add(AUTISTA); 
            add(DAE); 
            add(CAPOSERVIZIO); 
        } 
    }; 
    
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
	
	public Membro() {
		super(); 
		this.OkStatus = true; 
		this.errorList = new ArrayList<String>(); 
	}
	public Membro(String nome, String cognome, String qualifica) {
		super();
		this.OkStatus = true;
		this.errorList = new ArrayList<String>();
		this.setCognome(cognome);
		this.setNome(nome);
		this.setQualifica(qualifica);
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
