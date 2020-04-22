package emmaTommy.EmmaDataModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

import emmaTommy.DataModel.DateAdapterYYMMDDdash;

@XmlRootElement(name = "paziente")
@XmlType(name = "Paziente")
@XmlAccessorType (XmlAccessType.FIELD)
@XmlDiscriminatorNode("@@type")
public class Paziente extends EmmaDataModel {
	
	public Paziente() {
		super();
	}
	
	public Paziente(String esitoTrasportoPaziente) {
		super();
		this.setEsitoTrasporto(esitoTrasportoPaziente);
		this.validateObject();
	}
	
	public Paziente(String esitoTrasportoPaziente,
			String codicePazienteArrivo) {
		super();
		this.setEsitoTrasporto(esitoTrasportoPaziente);
		this.setCodicePazienteArrivo(codicePazienteArrivo);
		this.validateObject();
	}
	
	public Paziente(String nome, String cognome, String esitoTrasportoPaziente) {
		super();
		this.setNome(nome);
		this.setCognome(cognome);
		this.setEsitoTrasporto(esitoTrasportoPaziente);
		this.validateObject();
	}
	
	public Paziente(String nome, 
					String cognome, 
					String sesso, 
					LocalDate dataNascita, 
					int eta, 
					String esitoTrasportoPaziente) {
		super();
		this.setNome(nome);
		this.setCognome(cognome);
		this.setSesso(sesso);
		this.setDataNascita(dataNascita);
		this.setEta(eta);
		this.setEsitoTrasporto(esitoTrasportoPaziente);
		this.validateObject();
	}
	
	public Paziente(String nome, 
					String cognome, 
					String sesso, 
					LocalDate dataNascita, 
					int eta, 
					String pediatrico, 
					String esitoTrasportoPaziente) {
		super();
		this.setNome(nome);
		this.setCognome(cognome);
		this.setSesso(sesso);
		this.setDataNascita(dataNascita);
		this.setEta(eta);
		this.setPediatrico(pediatrico);
		this.setEsitoTrasporto(esitoTrasportoPaziente);
		this.validateObject();
	}
	
	public Paziente(String nome, 
			String cognome, 
			String sesso, 
			LocalDate dataNascita, 
			int eta, 
			String pediatrico, 
			String esitoTrasportoPaziente,
			String codicePazienteArrivo) {
		super();
		this.setNome(nome);
		this.setCognome(cognome);
		this.setSesso(sesso);
		this.setDataNascita(dataNascita);
		this.setEta(eta);		
		this.setPediatrico(pediatrico);
		this.setEsitoTrasporto(esitoTrasportoPaziente);
		this.setCodicePazienteArrivo(codicePazienteArrivo);
		this.validateObject();
	}

	public Paziente(String nome, 
			String cognome, 
			String sesso, 
			LocalDate dataNascita, 
			int eta, 
			String pediatrico, 
			String esitoTrasportoPaziente,
			String codicePazienteArrivo,
			int FC,
			int FR,
			String coscienza,
			String comuneResidenza) {
		super();
		this.setNome(nome);
		this.setCognome(cognome);
		this.setSesso(sesso);
		this.setDataNascita(dataNascita);
		this.setEta(eta);		
		this.setPediatrico(pediatrico);
		this.setEsitoTrasporto(esitoTrasportoPaziente);
		this.setCodicePazienteArrivo(codicePazienteArrivo);
		this.setFC(FC);
		this.setFR(FR);
		this.setCoscienza(coscienza);
		this.setComuneResidenza(comuneResidenza);
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
			logger.warn(errorMsg);
		}
		
		// Check Sesso
		errorMsg = this.getClass().getSimpleName() + ": ";
		if (this.sesso != null) {
			if (!this.sesso.isEmpty()) {
				if (!this.sesso.isBlank()) {
					if (this.sesso.compareToIgnoreCase(EmmaDataModelEnums.MALE_GENDER) != 0 
						&& this.sesso.compareToIgnoreCase(EmmaDataModelEnums.FEMALE_GENDER) != 0) {
						this.validState = false;
						errorMsg += "sesso wasn't an accepted value (" + this.sesso + "), setting it to blanck";
						this.errorList.add(errorMsg);
						logger.warn(errorMsg);
						this.sesso = "";
						this.validState = true;
					}
				}
			}
		}
		
		// Check Età
		errorMsg = this.getClass().getSimpleName() + ": ";
		if (this.eta < 0) {
			this.validState = false;
			errorMsg += "eta was less than zero" + this.eta + ")";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);			
		}
		
		// Check Pediatrico
		errorMsg = this.getClass().getSimpleName() + ": ";
		if (this.pediatrico != null) {
			if (!this.pediatrico.isEmpty()) {
				if (!this.pediatrico.isBlank()) {
					if (pediatrico.compareToIgnoreCase(EmmaDataModelEnums.PEDIATRIC_SI) != 0 
						&& pediatrico.compareToIgnoreCase(EmmaDataModelEnums.PEDIATRIC_NO) != 0) {
						this.validState = false;
						errorMsg += "pediatrico wasn't an accepted value (" + pediatrico + ")";
						this.errorList.add(errorMsg);
						logger.warn(errorMsg);
					}
				}
			}
		}
		
		// Esito Trasporto
		errorMsg = this.getClass().getSimpleName() + ": ";
		if (this.esitoTrasporto == null) {
			this.validState = false;
			errorMsg += "esitoTrasporto was NULL";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		else if (this.esitoTrasporto.isEmpty()) {
			this.validState = false;
			errorMsg += "esitoTrasporto was Empty";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		else if (this.esitoTrasporto.isBlank()) {
			this.validState = false;
			errorMsg += "esitoTrasporto was Blanck";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		else if (!EmmaDataModelEnums.acceptedTransportPazienteOutcome.contains(this.esitoTrasporto)) {
			this.validState = false;
			errorMsg += esitoTrasporto + "isn't in the accepted list of esiti trasporto (New One?)";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		
		// Codice Paziente
		errorMsg = this.getClass().getSimpleName() + ": ";
		if (this.codicePazienteArrivo != null) {
			if (!this.codicePazienteArrivo.isEmpty()) {
				if (!this.codicePazienteArrivo.isBlank()) {
					if (codicePazienteArrivo.compareToIgnoreCase(EmmaDataModelEnums.CODES_ROSSO) != 0 
						&& codicePazienteArrivo.compareToIgnoreCase(EmmaDataModelEnums.CODES_GIALLO) != 0
						&& codicePazienteArrivo.compareToIgnoreCase(EmmaDataModelEnums.CODES_VERDE) != 0) {
						this.validState = false;
						errorMsg += codicePazienteArrivo + " wasn't an accepted codice paziente (New One?) - ";
						this.errorList.add(errorMsg);
						logger.warn(errorMsg);
					}
				}
			}
		}
		
		// Check FR
		errorMsg = this.getClass().getSimpleName() + ": ";
		if (!EmmaDataModelEnums.acceptedFR.contains(this.FR)) {
			this.validState = false;
			errorMsg += FR + " wasn't an accepted FR (New One?) - ";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		
		// Check Coscienza
		errorMsg = this.getClass().getSimpleName() + ": ";
		if (this.coscienza != null) {
			if (!this.coscienza.isEmpty()) {
				if (!this.coscienza.isBlank()) {
					if (!EmmaDataModelEnums.acceptedCosciences.contains(this.coscienza)) {
						this.validState = false;
						errorMsg += coscienza + " wasn't an accepted coscienza (New One?) - ";
						this.errorList.add(errorMsg);
						logger.warn(errorMsg);
					}
				}
			}
		}
		
		// Return validState
		return this.validState;
	}
	
	/** type Attribute */
	@XmlAttribute(name = "type")
	protected String type = this.getClass().getSimpleName();	
	public void setType(String type) {
		this.type = type;
		logger.trace("::setType(): " + type);	
	}
	public String getType() {
		return this.type;
	}
	
	/** Cognome del Paziente */	
	@XmlElement(name = "pz-ds-cognome", required = false)	
	protected String cognome;
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getCognome() {
		return this.cognome;
	}
	
	/** Nome del Paziente */	
	@XmlElement(name = "pz-ds-nome", required = false)	
	protected String nome;
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNome() {
		return this.nome;
	}
	
	/** Sesso del Paziente [M|F]*/	
	@XmlElement(name = "pz-ac-sesso", required = false)	
	protected String sesso;
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public String getSesso() {
		return this.sesso;
	}
	
	/** Data di Nascita del Paziente */	
	@XmlElement(name = "pz-dt-nascita", required = false)
	@XmlJavaTypeAdapter(DateAdapterYYMMDDdash.class)
	protected LocalDate dataNascita;
	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}
	public LocalDate getDataNascita() {
		return this.dataNascita; 
	}
	public String getDataNascitaStr() {
		String DATE_FORMAT_STRING = "yyyyMMdd";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT_STRING); 
		return dtf.format(this.dataNascita);
	}
	
	/** Eta del Paziente */	
	@XmlElement(name = "pz-vl-eta", required = false)	
	protected int eta;
	public void setEta(int eta) {
		this.eta = eta;
	}
	public int getEta() {
		return this.eta;
	}
	
	/** Indicatore Paziente Pediatrico [S|N] */	
	@XmlElement(name = "pz-fl-pz-pediatrico", required = false)	
	protected String pediatrico;
	public void setPediatrico(String pediatrico) {
		this.pediatrico = pediatrico;
	}
	public String getPediatrico() {
		return this.pediatrico;
	}
	
	/** Esito del Trasporto */	
	@XmlElement(name = "pz-ds-esito", required = true)	
	protected String esitoTrasporto;
	public void setEsitoTrasporto(String esitoTrasporto) {
		this.esitoTrasporto = esitoTrasporto;
	}
	public String getEsitoTrasporto() {
		return this.esitoTrasporto;
	}
	
	/** Codice Riscontrato All'Arrivo */	
	@XmlElement(name = "pz-id-codice-r", required = false)	
	protected String codicePazienteArrivo;
	public void setCodicePazienteArrivo(String codicePazienteArrivo) {
		this.codicePazienteArrivo = codicePazienteArrivo;
	}
	public String getCodicePazienteArrivo() {
		return this.codicePazienteArrivo;
	}
	
	/** FC Del Paziente	*/	
	@Deprecated
	@XmlElement(name = "pz-id-polso", required = false)	
	protected int FC;
	public void setFC(int FC) {
		this.FC = FC;
	}
	public int getFC() {
		return this.FC;
	}
	
	/** FR Del Paziente */		
	@XmlElement(name = "pz-id-respiro", required = false)	
	protected int FR;
	public void setFR(int FR) {
		this.FR = FR;
	}
	public int getFR() {
		return this.FR;
	}
	
	/** Stato di Coscienza del Paziente */	
	@XmlElement(name = "pz-cd-coscienza", required = false)	
	protected String coscienza;
	public void setCoscienza(String coscienza) {
		this.coscienza = coscienza;
	}
	public String getCoscienza() {
		return this.coscienza;
	}
	
	/** Comune di Residenza */	
	@XmlElement(name = "pz-ds-loc-res", required = false)	
	protected String comuneResidenza;
	public void setComuneResidenza(String comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}
	public String getComuneResidenza() {
		return this.comuneResidenza;
	}
	
	

	@Override
	public String toString() {
		return "Paziente [type=" + type + ", cognome=" + cognome + ", nome=" + nome + ", sesso=" + sesso
				+ ", dataNascita=" + dataNascita + ", eta=" + eta + ", pediatrico=" + pediatrico + ", esitoTrasporto="
				+ esitoTrasporto + ", codicePazienteArrivo=" + codicePazienteArrivo + ", FC=" + FC + ", FR=" + FR
				+ ", coscienza=" + coscienza + ", comuneResidenza=" + comuneResidenza + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + FC;
		result = prime * result + FR;
		result = prime * result + ((codicePazienteArrivo == null) ? 0 : codicePazienteArrivo.hashCode());
		result = prime * result + ((cognome == null) ? 0 : cognome.hashCode());
		result = prime * result + ((comuneResidenza == null) ? 0 : comuneResidenza.hashCode());
		result = prime * result + ((coscienza == null) ? 0 : coscienza.hashCode());
		result = prime * result + ((dataNascita == null) ? 0 : dataNascita.hashCode());
		result = prime * result + ((esitoTrasporto == null) ? 0 : esitoTrasporto.hashCode());
		result = prime * result + eta;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((pediatrico == null) ? 0 : pediatrico.hashCode());
		result = prime * result + ((sesso == null) ? 0 : sesso.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Paziente))
			return false;
		Paziente other = (Paziente) obj;
		if (FC != other.getFC())
			return false;
		if (FR != other.getFR())
			return false;
		if (codicePazienteArrivo == null) {
			if (other.getCodicePazienteArrivo() != null)
				return false;
		} else if (!codicePazienteArrivo.equals(other.getCodicePazienteArrivo()))
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
		if (coscienza == null) {
			if (other.getCoscienza() != null)
				return false;
		} else if (!coscienza.equals(other.getCoscienza()))
			return false;
		if (dataNascita == null) {
			if (other.getDataNascita() != null)
				return false;
		} else if (!dataNascita.equals(other.getDataNascita()))
			return false;
		if (esitoTrasporto == null) {
			if (other.getEsitoTrasporto() != null)
				return false;
		} else if (!esitoTrasporto.equals(other.getEsitoTrasporto()))
			return false;
		if (eta != other.getEta())
			return false;
		if (nome == null) {
			if (other.getNome() != null)
				return false;
		} else if (!nome.equals(other.getNome()))
			return false;
		if (pediatrico == null) {
			if (other.getPediatrico() != null)
				return false;
		} else if (!pediatrico.equals(other.getPediatrico()))
			return false;
		if (sesso == null) {
			if (other.getSesso() != null)
				return false;
		} else if (!sesso.equals(other.getSesso()))
			return false;
		if (type == null) {
			if (other.getType() != null)
				return false;
		} else if (!type.equals(other.getType()))
			return false;
		return true;
	}
	
	
	
	
}
