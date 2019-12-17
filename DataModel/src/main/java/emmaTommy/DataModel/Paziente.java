package emmaTommy.DataModel;

import java.util.Date;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "paziente")
@XmlAccessorType(XmlAccessType.FIELD)
public class Paziente extends toDataFormatClass {
	
	public Paziente() {super();}
	
	/** Cognome del Paziente */	
	@XmlElement(name = "pz-ds-cognome", required = true)	
	protected String cognome;
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getCognome() {
		return this.cognome;
	}
	
	/** Nome del Paziente */	
	@XmlElement(name = "pz-ds-nome", required = true)	
	protected String nome;
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNome() {
		return this.nome;
	}
	
	/** Sesso del Paziente [M|F]*/	
	@XmlElement(name = "pz-ac-sesso", required = true)	
	protected String sesso;
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public String getSesso() {
		return this.sesso;
	}
	
	/** Data di Nascita del Paziente */	
	@XmlElement(name = "pz-dt-nascita", required = true)	
	protected Date dataNascita;
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	public Date getDataNascita() {
		return this.dataNascita; 
	}
	
	/** Eta del Paziente */	
	@XmlElement(name = "pz-vl-eta", required = true)	
	protected int eta;
	public void setEta(int eta) {
		this.eta = eta;
	}
	public int getEta() {
		return this.eta;
	}
	
	/** Indicatore Paziente Pediatrico [S|N] */	
	@XmlElement(name = "pz-fl-pz-pediatrico", required = true)	
	protected String pediatrico;
	public void setPediatrico(String pediatrico) {
		this.pediatrico = pediatrico;
	}
	public String getPediatrico() {
		return this.pediatrico;
	}
	
	/** Esito del Trasporto */	
	@XmlElement(name = "pz-ds-esito", required = true)	
	protected String esito;
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public String getEsito() {
		return this.esito;
	}
	
	/** Codice Riscontrato All'Arrivo */	
	@XmlElement(name = "pz-id-codice-r", required = true)	
	protected String codice;
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getCodice() {
		return this.codice;
	}
	
	/** FC Del Paziente	*/	
	@XmlElement(name = "pz-id-polso", required = false)	
	protected String FC;
	public void setFC(String FC) {
		this.FC = FC;
	}
	public String getFC() {
		return this.FC;
	}
	
	/** FR Del Paziente */		
	@XmlElement(name = "pz-id-respiro", required = false)	
	protected String FR;
	public void setFR(String FR) {
		this.FR = FR;
	}
	public String getFR() {
		return this.FR;
	}
	
	/** Stato di Coscienza del Paziente */
	
	@XmlElement(name = "pz-cd-coscienza", required = true)	
	protected String coscienza;
	public void setCoscienza(String coscienza) {
		this.coscienza = coscienza;
	}
	public String getCoscienza() {
		return this.coscienza;
	}
	
	/** Comune di Residenza */
	
	@XmlElement(name = "pz-ds-loc-res", required = true)	
	protected String comuneResidenza;
	public void setComuneResidenza(String comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}
	public String getComuneResidenza() {
		return this.comuneResidenza;
	}
	
}
