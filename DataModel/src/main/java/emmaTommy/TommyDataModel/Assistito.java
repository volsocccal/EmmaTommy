package emmaTommy.TommyDataModel;

import java.util.Date;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import emmaTommy.DataModel.DateAdapterYYMMDD;

@XmlRootElement(name = "assistito")
@XmlType(name = "Assistito")
@XmlAccessorType (XmlAccessType.FIELD)
public class Assistito extends TommyDataModel {
	
	public Assistito() {
		super();
	}
	
	public Assistito(String codiceFiscale,
					String nome, 
					String cognome, 
					String sesso, 
					Date dataNascita, 
					String residenza) {
		super();
		this.setCodiceFiscale(codiceFiscale);
		this.setNome(nome);
		this.setCognome(cognome);
		this.setDataNascita(dataNascita);
		this.setSesso(sesso);
		this.setComuneResidenza(residenza);
		this.validateObject();
	}
	
	
	
	/** validateObject
	 * type: not null, not empty, not blanck, equals to the simple class name
	 * nome: -
	 * cognome: -
	 * sesso: if not null, not empty, not blanck, can be only M or F (Male or Female)
	 * data di nascità: -
	 * età: zero or more
	 * pediatrico: if not null, not empty, not blanck, can be only S or N (Yes or No)
	 * esitoTrasporto: not null, not empty, not blanck, part of the Esiti.accepted_esiti List
	 * FC: always null (and ignored)
	 * FR: 0/1/2/3
	 * coscenza: if not null, not empty, not blanck, can be only Not Set or one in the AVPU scale
	 * comune: -
	 * 
	 * @return true if the object is valid, false otherwise
	 */
	@XmlTransient
	public Boolean validateObject() {
		String errorMsg = this.getClass().getSimpleName() + ": ";
		
		// Check Sesso
		if (this.sesso != null) {
			if (!this.sesso.isEmpty()) {
				if (!this.sesso.isBlank()) {
					if (sesso.compareToIgnoreCase(TommyDataModelEnums.MALE_GENDER) != 0 
						&& sesso.compareToIgnoreCase(TommyDataModelEnums.FEMALE_GENDER) != 0) {
						this.validState = false;
						errorMsg += "sesso wasn't an accepted value (" + sesso + ")";
						this.errorList.add(errorMsg);
						logger.warn(errorMsg);
					}
				}
			}
		}
		
		// Return validState
		return this.validState;
	}
	
	/** codice_fiscale" : "type:String:required" */	
	@XmlElement(name = "codice_fiscale", required = true)	
	protected String codice_fiscale;
	public void setCodiceFiscale(String codice_fiscale) {
		this.codice_fiscale = codice_fiscale;
	}
	public String getCodiceFiscale() {
		return this.codice_fiscale;
	}
	
	/** cognome" : "type:String:required" */	
	@XmlElement(name = "cognome", required = true)	
	protected String cognome;
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getCognome() {
		return this.cognome;
	}
	
	/** nome" : "type:String:required" */	
	@XmlElement(name = "nome", required = true)	
	protected String nome;
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNome() {
		return this.nome;
	}
	
	/** sesso" : "type:M/F"*/	
	@XmlElement(name = "sesso", required = false)	
	protected String sesso;
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public String getSesso() {
		return this.sesso;
	}
	
	/** datanascita" : "type:YYYYMMDD"*/	
	@XmlElement(name = "datanascita", required = false)
	@XmlJavaTypeAdapter(DateAdapterYYMMDD.class)
	protected Date dataNascita;
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	public Date getDataNascita() {
		return this.dataNascita; 
	}
	
	
	/** residenza" : "type:String" */	
	@XmlElement(name = "residenza", required = false)	
	protected String comuneResidenza;
	public void setComuneResidenza(String comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}
	public String getComuneResidenza() {
		return this.comuneResidenza;
	}
	
	
	/** note" : "type:String" */	
	@XmlElement(name = "note", required = false)	
	protected String note;
	public void setNote(String note) {
		this.note = note;
	}
	public String getNote() {
		return this.note;
	}
	
	

	@Override
	public String toString() {
		return "Paziente [codiceFiscale=" + codice_fiscale + ", cognome=" + cognome + ", nome=" + nome + ", sesso=" + sesso
				+ ", dataNascita=" + dataNascita + ", comuneResidenza=" + comuneResidenza + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codice_fiscale == null) ? 0 : codice_fiscale.hashCode());
		result = prime * result + ((cognome == null) ? 0 : cognome.hashCode());
		result = prime * result + ((comuneResidenza == null) ? 0 : comuneResidenza.hashCode());
		result = prime * result + ((dataNascita == null) ? 0 : dataNascita.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((sesso == null) ? 0 : sesso.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Assistito))
			return false;
		Assistito other = (Assistito) obj;
		if (codice_fiscale == null) {
			if (other.getCodiceFiscale() != null)
				return false;
		} else if (!codice_fiscale.equals(other.getCodiceFiscale()))
			return false;
		if (cognome == null) {
			if (other.getCognome() != null)
				return false;
		} else if (!cognome.equals(other.getCognome()))
			return false;
		if (comuneResidenza == null) {
			if (other.comuneResidenza != null)
				return false;
		} else if (!comuneResidenza.equals(other.getComuneResidenza()))
			return false;
		if (dataNascita == null) {
			if (other.getDataNascita() != null)
				return false;
		} else if (!dataNascita.equals(other.getDataNascita()))
			return false;
		if (nome == null) {
			if (other.getNome() != null)
				return false;
		} else if (!nome.equals(other.getNome()))
			return false;
		if (sesso == null) {
			if (other.getSesso() != null)
				return false;
		} else if (!sesso.equals(other.getSesso()))
			return false;
		return true;
	}
	
	
	
	
}
