package emmaTommy.EmmaDataModel;

import java.time.LocalDateTime;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.persistence.oxm.annotations.XmlDiscriminatorNode;

import emmaTommy.DataModel.DateTimeAdapterYYYYMMDDTHHmmssZ;
import emmaTommy.EmmaDataModel.Membri;
import emmaTommy.EmmaDataModel.Pazienti;
import emmaTommy.EmmaDataModel.Tratte;

@XmlRootElement(name = "missione")
@XmlType(name = "Missione")
@XmlAccessorType (XmlAccessType.FIELD)
@XmlDiscriminatorNode("@@type")
public class Missione extends EmmaDataModel {

	/** Empty Constructor */
	public Missione () {super();}
	
	/** validateObject
	 * type: not null, not empty, not blanck, equals to the simple class name
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
			errorMsg = this.getClass().getSimpleName() + ": ";
		}
		else if (this.type.isEmpty()) {
			this.validState = false;
			errorMsg += "type was Empty";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
			errorMsg = this.getClass().getSimpleName() + ": ";
		}
		else if (this.type.isBlank()) {
			this.validState = false;
			errorMsg += "type was Blanck";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
			errorMsg = this.getClass().getSimpleName() + ": ";
		}
		else if (this.type.compareTo(this.getClass().getSimpleName()) != 0) {
			this.validState = false;
			errorMsg += "type was diffent from ClassName";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
			errorMsg = this.getClass().getSimpleName() + ": ";
		}
				
		// Pazienti
		errorMsg = this.getClass().getSimpleName() + ": ";
		if (this.pazienti == null) {
			this.validState = false;
			errorMsg += "pazienti list was null";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
			errorMsg = this.getClass().getSimpleName() + ": ";
		} else {
			Boolean pazientiValidState = this.pazienti.validateObject();
			if (!pazientiValidState) {
				this.validState = false;
				this.errorList.addAll(this.pazienti.getErrorList());
			}
		}
		
		// Tratte
		errorMsg = this.getClass().getSimpleName() + ": ";
		if (this.tratte == null) {
			this.validState = false;
			errorMsg += "tratte list was null";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
			errorMsg = this.getClass().getSimpleName() + ": ";
		} else {
			Boolean tratteValidState = this.tratte.validateObject();
			if (!tratteValidState) {
				this.validState = false;
				this.errorList.addAll(this.tratte.getErrorList());
			}
		}
		
		// Membri Equipaggio
		errorMsg = this.getClass().getSimpleName() + ": ";
		if (this.membri == null) {
			this.validState = false;
			errorMsg += "membri list was null";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
			errorMsg = this.getClass().getSimpleName() + ": ";
		} else {
			Boolean membriValidState = this.membri.validateObject();
			if (!membriValidState) {
				this.validState = false;
				this.errorList.addAll(this.membri.getErrorList());
			}
		}
		
		
		// Esito Trasporto
		errorMsg = this.getClass().getSimpleName() + ": ";
		if (this.esitoMissione == null) {
			this.validState = false;
			errorMsg += "esitoMissione was NULL";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		else if (this.esitoMissione.isEmpty()) {
			this.validState = false;
			errorMsg += "esitoMissione was Empty";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		else if (this.esitoMissione.isBlank()) {
			this.validState = false;
			errorMsg += "esitoMissione was Blanck";
			this.errorList.add(errorMsg);
			logger.warn(errorMsg);
		}
		else if (!EmmaDataModelEnums.acceptedMissioniOutcome.contains(this.esitoMissione)) {
			this.validState = false;
			errorMsg += esitoMissione + "isn't in the accepted list of esiti Missione (New One?)";
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
		logger.trace("::setType(): " + type);	
	}
	public String getType() {
		return this.type;
	}
	
	/** Elenco Delle Tratte della Missione */
	@XmlElement(name = "tratte", required = true)
	protected Tratte tratte = null;	
	public void setTratte(Tratte tratte) {
		this.tratte = tratte;
	}
	public Tratte getTratte() {
		return this.tratte;
	}
	
	/** Elenco Dei Membri dell'Equipaggio della Missione */
	@XmlElement(name = "membri", required = true)	
	protected Membri membri = null;	
	public void setMembri(Membri membri) {
		this.membri = membri;
	}
	public Membri getMembri() {
		return this.membri;
	}
	
	/** Elenco Dei Pazienti della Missione */
	@XmlElement(name = "pazienti", required = true)	
	protected Pazienti pazienti = null;	
	public void setPazienti(Pazienti pazienti) {
		this.pazienti = pazienti;
	}
	public Pazienti getPazienti() {
		return this.pazienti;
	}
	
	/** Luogo di Destinazione della Missione */
	@XmlElement(name = "lg-ds-luogo", required = true)	
	private String luogoDestinazioneMissione;
	public void setLuogoDestinazioneMissione(String luogoDestinazioneMissione) {
		this.luogoDestinazioneMissione = luogoDestinazioneMissione;
	}
	public String getLuogoDestinazioneMissione() {
		return this.luogoDestinazioneMissione;
	}
	
	/** Codice Ente */
	@XmlElement(name = "mi-ac-ente", required = true)	
	protected String codiceEnte;
	public void setCodiceEnte(String codiceEnte) {
		this.codiceEnte = codiceEnte;
	}
	public String getCodiceEnte() {
		return this.codiceEnte;
	}
	
	/** Codice del Mezzo Impiegato */
	@XmlElement(name = "mi-cd-mezzo", required = true)	
	protected String codiceMezzo;
	public void setCodiceMezzo(String codiceMezzo) {
		this.codiceMezzo = codiceMezzo;
	}
	public String getCodiceMezzo() {
		return this.codiceMezzo;
	}
	
	/** Tipo di Convenzione tra ENTE e AREYU */
	@XmlElement(name = "mi-ds-convenz", required = true)	
	protected String convenzioneEnte;
	public void setConvenzioneEnte(String convenzioneEnte) {
		this.convenzioneEnte = convenzioneEnte;
	}
	public String getConvenzioneEnte() {
		return this.convenzioneEnte;
	}
	
	/** Esito della Missione */
	@XmlElement(name = "mi-ds-esito", required = true)	
	protected String esitoMissione;
	public void setEsitoMissione (String esitoMissione) {
		this.esitoMissione = esitoMissione;
	}
	public String getEsitoMissione () {
		return this.esitoMissione;
	}
	
	/** Stazionamento Assegnato a Fine Missione */
	@XmlElement(name = "mi-ds-lg-dest", required = true)	
	protected String stazionamentoFineMissione;
	public void setStazionamentoFineMissione (String stazionamentoFineMissione) {
		this.stazionamentoFineMissione = stazionamentoFineMissione;
	}
	public String getStazionamentoFineMissione() {
		return this.stazionamentoFineMissione;
	}	
	
	/** Posizione di Stazionamento del Mezzo */
	@XmlElement(name = "mi-ds-punsta", required = true)	
	protected String stazionamentoFineMissionePosMezzo;
	public void setStazionamentoFineMissionePosMezzo (String stazionamentoFineMissionePosMezzo) {
		this.stazionamentoFineMissionePosMezzo = stazionamentoFineMissionePosMezzo;
	}
	public String getStazionamentoFineMissionePosMezzo() {
		return this.stazionamentoFineMissionePosMezzo;
	}
	
	/** Data di Fine Missione */
	@XmlElement(name = "mi-dt-fine-mi", required = true)	
	@XmlJavaTypeAdapter(DateTimeAdapterYYYYMMDDTHHmmssZ.class)
	protected LocalDateTime fineMissione;	
	public void setFineMissione (LocalDateTime fineMissione) {
		this.fineMissione = fineMissione;
	}
	public LocalDateTime getFineMissione() {
		return this.fineMissione;
	}
	
	/** Data di Inizio Rientro */
	@XmlElement(name = "mi-dt-inizio-r", required = true)	
	@XmlJavaTypeAdapter(DateTimeAdapterYYYYMMDDTHHmmssZ.class)
	protected LocalDateTime inizioRientro;
	public void setInizioRientro(LocalDateTime inizioRientro) {
		this.inizioRientro = inizioRientro;
	}
	public LocalDateTime getInizioRientro() {
		return this.inizioRientro;
	}
	
	/** Data di Inizio Missione (Creazione Scheda) */
	@XmlElement(name = "mi-dt-missione", required = true)
	@XmlJavaTypeAdapter(DateTimeAdapterYYYYMMDDTHHmmssZ.class)
	protected LocalDateTime inizioMissione;
	public void setInizioMissione(LocalDateTime inizioMissione) {
		this.inizioMissione = inizioMissione;
	}
	public LocalDateTime getInizioMissione() {
		return this.inizioMissione;
	}
	
	/** Data di Partenza del Mezzo */
	@XmlElement(name = "mi-dt-partenza", required = true)
	@XmlJavaTypeAdapter(DateTimeAdapterYYYYMMDDTHHmmssZ.class)
	protected LocalDateTime partenzaMezzo;
	public void setPartenzaMezzo (LocalDateTime partenzaMezzo) {
		this.partenzaMezzo = partenzaMezzo;
	}
	public LocalDateTime getPartenzaMezzo() {
		return this.partenzaMezzo;
	}
	
	/** Codice Evento */
	@XmlElement(name = "mi-id-codice-e", required = true)	
	protected String codiceEvento; 
	public void setCodiceEvento(String codiceEvento) {
		this.codiceEvento = codiceEvento;
	}
	public String getCodiceEvento() {
		return this.codiceEvento;
	}
	
	/** Codice Trasporto */
	@XmlElement(name = "mi-id-codice-t", required = true)	
	protected String codiceTrasporto; 
	public void setCodiceTrasporto(String codiceTrasporto) {
		this.codiceTrasporto = codiceTrasporto;
	}
	public String getCodiceTrasporto() {
		return this.codiceTrasporto;
	}
	
	/** Codice Missione Precedente se Mezzo Intercettato */
	@XmlElement(name = "mi-id-miss-int", required = true)	
	protected int codiceMissionePrecedente;
	public void setCodiceMissionePrecedente(int codiceMissionePrecedente) {
		this.codiceMissionePrecedente = codiceMissionePrecedente;
	}
	public int getCodiceMissionePrecedente() {
		return this.codiceMissionePrecedente;
	}
	
	/** Codice Missione da 10 Cifre 
	  * del tipo AASXXXXXXX dove
      * AA indica l’anno (es 19 per il 2019, 20 per il 2020)
      * S indica la SOREU di Riferimento
                    (a) Bergamo (Soreu Alpina): 1
                    (b) Como (Soreu Laghi): 3
                    (c) Milano (Soreu Metropolitana): 5
                    (d) Pavia (Soreu Pianura): 7
      * XXXXXXX è il numero effettivo di missione da 7 cifre, con partenza dal primo giorno dell’anno
	  * 
	  */
	@XmlElement(name = "mi-id-missione", required = true)	
	protected int codiceMissione;
	public void setCodiceMissione(int codiceMissione) {
		this.codiceMissione = codiceMissione;
	}
	public int getCodiceMissione() {
		return this.codiceMissione;
	}
	
	/** Numero Totale di Km Percorsi */
	@XmlElement(name = "mi-vl-totkm", required = true)	
	protected int totKMPercorsi;
	public void setTotKMPercorsi (int totKMPercorsi) {
		this.totKMPercorsi = totKMPercorsi;
	}
	public int getTotKMPercorsi() {
		return this.totKMPercorsi; 
	}
	
	/** Comune dove avviene l'intervento */
	@XmlElement(name = "sc-ds-comune", required = true)	
	protected String comuneIntervento;
	public void setComuneIntervento(String comuneIntervento) {
		this.comuneIntervento = comuneIntervento;
	}
	public String getComuneIntervento() {
		return this.comuneIntervento;
	}
	
	/** Motivo della Chiamata */
	@XmlElement(name = "sc-ds-motivo", required = true)	
	protected String motivoChiamata;
	public void setMotivoChiamata(String motivoChiamata) {
		this.motivoChiamata = motivoChiamata;
	}
	public String getMotivoChiamata() {
		return this.motivoChiamata;
	}
	
	/** Motivo della Chiamata - Dettaglio */
	@XmlElement(name = "sc-ds-det-mot", required = true)	
	protected String motivoChiamataDettaglio;
	public void setMotivoChiamataDettaglio(String motivoChiamataDettaglio) {
		this.motivoChiamata = motivoChiamataDettaglio;
	}
	public String getMotivoChiamataDettaglio() {
		return this.motivoChiamataDettaglio;
	}
	
	/** Ulteriori Informazioni Sull'Evento (Riferimenti) */
	@XmlElement(name = "sc-ds-riferimen", required = true)	
	protected String riferimenti;
	public void setRiferimenti(String riferimenti) {
		this.riferimenti = riferimenti;
	}
	public String getRiferimenti() {
		return this.riferimenti;
	}
	
	/** Tipo di Via dell'Indirizzo del Soccorso */
	@XmlElement(name = "sc-ds-tp-via-1", required = true)	
	protected String indirizzoSoccorsoTipoVia;
	public void setIndirizzoSoccorsoTipoVia(String indirizzoSoccorsoTipoVia) {
		this.indirizzoSoccorsoTipoVia = indirizzoSoccorsoTipoVia;
	}
	public String getIndirizzoSoccorsoTipoVia() {
		return indirizzoSoccorsoTipoVia;
	}	

	/** Via dell'Indirizzo del Soccorso */
	@XmlElement(name = "sc-ds-via-1", required = true)	
	protected String indirizzoSoccorsoVia;
	public void setIndirizzoSoccorsoVia(String indirizzoSoccorsoVia) {
		this.indirizzoSoccorsoVia = indirizzoSoccorsoVia;
	}
	public String getIndirizzoSoccorsoVia() {
		return indirizzoSoccorsoVia;
	}
	
	/** Via - Incrocio  dell'Indirizzo del Soccorso */
	@XmlElement(name = "sc-ds-via-2", required = true)	
	protected String indirizzoSoccorsoViaIncrocio;
	public void setIndirizzoSoccorsoViaIncrocio(String indirizzoSoccorsoViaIncrocio) {
		this.indirizzoSoccorsoViaIncrocio = indirizzoSoccorsoViaIncrocio;
	}
	public String getIndirizzoSoccorsoViaIncrocio() {
		return this.indirizzoSoccorsoViaIncrocio;
	}
	
	/** Numero Civico dell'Indirizzo del Soccorso */
	@XmlElement(name = "sc-vl-civico-v1", required = true)	
	protected String indirizzoSoccorsoCivico;
	public void setIndirizzoSoccorsoCivico(String indirizzoSoccorsoCivico) {
		this.indirizzoSoccorsoCivico = indirizzoSoccorsoCivico;
	}
	public String getIndirizzoSoccorsoCivico() {
		return this.indirizzoSoccorsoCivico;
	}
	
	/** Numero Civico Barrato dell'Indirizzo del Soccorso */
	@XmlElement(name = "sc-vl-barra-v1", required = true)	
	protected String indirizzoSoccorsoCivicoBarra;
	public void setIndirizzoSoccorsoCivicoBarra(String indirizzoSoccorsoCivicoBarra) {
		this.indirizzoSoccorsoCivico = indirizzoSoccorsoCivicoBarra;
	}
	public String getIndirizzoSoccorsoCivicoBarra() {
		return this.indirizzoSoccorsoCivicoBarra;
	}
	
	
	/** Località di Partenza della Missione */
	@XmlElement(name = "mi-ds-loc-part", required = true)	
	protected String partenzaMissioneLocalita;
	public void setPartenzaMissioneLocalita(String partenzaMissioneLocalita) {
		this.partenzaMissioneLocalita = partenzaMissioneLocalita;
	}
	public String getPartenzaMissioneLocalita() {
		return this.partenzaMissioneLocalita;
	}
	
	/** Via di Partenza della Missione */
	@XmlElement(name = "mi-ds-via-part", required = true)	
	protected String partenzaMissioneVia;
	public void setPartenzaMissioneVia(String partenzaMissioneVia) {
		this.partenzaMissioneVia =partenzaMissioneVia;
	}
	public String getPartenzaMissioneVia() {
		return this.partenzaMissioneVia;
	}
	
	
	@Override
	public String toString() {
		return "Missione [tratte=" + tratte.toString() + ", membri=" + membri.toString() + ", pazienti=" + pazienti.toString()
				+ ", luogoDestinazioneMissione=" + luogoDestinazioneMissione + ", codiceEnte=" + codiceEnte
				+ ", codiceMezzo=" + codiceMezzo + ", convenzioneEnte=" + convenzioneEnte + ", esitoMissione="
				+ esitoMissione + ", stazionamentoFineMissione=" + stazionamentoFineMissione
				+ ", stazionamentoFineMissionePosMezzo=" + stazionamentoFineMissionePosMezzo + ", fineMissione="
				+ fineMissione.toString() + ", inizioRientro=" + inizioRientro.toString() + ", inizioMissione=" + inizioMissione.toString()
				+ ", partenzaMezzo=" + partenzaMezzo.toString() + ", codiceEvento=" + codiceEvento + ", codiceTrasporto="
				+ codiceTrasporto + ", codiceMissionePrecedente=" + codiceMissionePrecedente + ", codiceMissione="
				+ codiceMissione + ", totKMPercorsi=" + totKMPercorsi + ", comuneIntervento=" + comuneIntervento
				+ ", motivoChiamata=" + motivoChiamata + ", motivoChiamataDettaglio=" + motivoChiamataDettaglio
				+ ", riferimenti=" + riferimenti + ", indirizzoSoccorsoTipoVia=" + indirizzoSoccorsoTipoVia
				+ ", indirizzoSoccorsoVia=" + indirizzoSoccorsoVia + ", indirizzoSoccorsoViaIncrocio="
				+ indirizzoSoccorsoViaIncrocio + ", indirizzoSoccorsoCivico=" + indirizzoSoccorsoCivico
				+ ", indirizzoSoccorsoCivicoBarra=" + indirizzoSoccorsoCivicoBarra + ", partenzaMissioneLocalita="
				+ partenzaMissioneLocalita + ", partenzaMissioneVia=" + partenzaMissioneVia + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codiceEnte == null) ? 0 : codiceEnte.hashCode());
		result = prime * result + ((codiceEvento == null) ? 0 : codiceEvento.hashCode());
		result = prime * result + ((codiceMezzo == null) ? 0 : codiceMezzo.hashCode());
		result = prime * result + codiceMissione;
		result = prime * result + codiceMissionePrecedente;
		result = prime * result + ((codiceTrasporto == null) ? 0 : codiceTrasporto.hashCode());
		result = prime * result + ((comuneIntervento == null) ? 0 : comuneIntervento.hashCode());
		result = prime * result + ((convenzioneEnte == null) ? 0 : convenzioneEnte.hashCode());
		result = prime * result + ((esitoMissione == null) ? 0 : esitoMissione.hashCode());
		result = prime * result + ((fineMissione == null) ? 0 : fineMissione.hashCode());
		result = prime * result + ((indirizzoSoccorsoCivico == null) ? 0 : indirizzoSoccorsoCivico.hashCode());
		result = prime * result
				+ ((indirizzoSoccorsoCivicoBarra == null) ? 0 : indirizzoSoccorsoCivicoBarra.hashCode());
		result = prime * result + ((indirizzoSoccorsoTipoVia == null) ? 0 : indirizzoSoccorsoTipoVia.hashCode());
		result = prime * result + ((indirizzoSoccorsoVia == null) ? 0 : indirizzoSoccorsoVia.hashCode());
		result = prime * result
				+ ((indirizzoSoccorsoViaIncrocio == null) ? 0 : indirizzoSoccorsoViaIncrocio.hashCode());
		result = prime * result + ((inizioMissione == null) ? 0 : inizioMissione.hashCode());
		result = prime * result + ((inizioRientro == null) ? 0 : inizioRientro.hashCode());
		result = prime * result + ((luogoDestinazioneMissione == null) ? 0 : luogoDestinazioneMissione.hashCode());
		result = prime * result + ((membri == null) ? 0 : membri.hashCode());
		result = prime * result + ((motivoChiamata == null) ? 0 : motivoChiamata.hashCode());
		result = prime * result + ((motivoChiamataDettaglio == null) ? 0 : motivoChiamataDettaglio.hashCode());
		result = prime * result + ((partenzaMezzo == null) ? 0 : partenzaMezzo.hashCode());
		result = prime * result + ((partenzaMissioneLocalita == null) ? 0 : partenzaMissioneLocalita.hashCode());
		result = prime * result + ((partenzaMissioneVia == null) ? 0 : partenzaMissioneVia.hashCode());
		result = prime * result + ((pazienti == null) ? 0 : pazienti.hashCode());
		result = prime * result + ((riferimenti == null) ? 0 : riferimenti.hashCode());
		result = prime * result + ((stazionamentoFineMissione == null) ? 0 : stazionamentoFineMissione.hashCode());
		result = prime * result
				+ ((stazionamentoFineMissionePosMezzo == null) ? 0 : stazionamentoFineMissionePosMezzo.hashCode());
		result = prime * result + totKMPercorsi;
		result = prime * result + ((tratte == null) ? 0 : tratte.hashCode());
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
		Missione other = (Missione) obj;
		if (codiceEnte == null) {
			if (other.getCodiceEnte() != null)
				return false;
		} else if (!codiceEnte.equals(other.getCodiceEnte()))
			return false;
		if (codiceEvento == null) {
			if (other.getCodiceEvento() != null)
				return false;
		} else if (!codiceEvento.equals(other.getCodiceEvento()))
			return false;
		if (codiceMezzo == null) {
			if (other.getCodiceMezzo() != null)
				return false;
		} else if (!codiceMezzo.equals(other.getCodiceMezzo()))
			return false;
		if (codiceMissione != other.getCodiceMissione())
			return false;
		if (codiceMissionePrecedente != other.getCodiceMissionePrecedente())
			return false;
		if (codiceTrasporto == null) {
			if (other.getCodiceTrasporto() != null)
				return false;
		} else if (!codiceTrasporto.equals(other.getCodiceTrasporto()))
			return false;
		if (comuneIntervento == null) {
			if (other.getComuneIntervento() != null)
				return false;
		} else if (!comuneIntervento.equals(other.getComuneIntervento()))
			return false;
		if (convenzioneEnte == null) {
			if (other.getConvenzioneEnte() != null)
				return false;
		} else if (!convenzioneEnte.equals(other.getConvenzioneEnte()))
			return false;
		if (esitoMissione == null) {
			if (other.getEsitoMissione() != null)
				return false;
		} else if (!esitoMissione.equals(other.getEsitoMissione()))
			return false;
		if (fineMissione == null) {
			if (other.getFineMissione() != null)
				return false;
		} else if (!fineMissione.equals(other.getFineMissione()))
			return false;
		if (indirizzoSoccorsoCivico == null) {
			if (other.getIndirizzoSoccorsoCivico() != null)
				return false;
		} else if (!indirizzoSoccorsoCivico.equals(other.getIndirizzoSoccorsoCivico()))
			return false;
		if (indirizzoSoccorsoCivicoBarra == null) {
			if (other.getIndirizzoSoccorsoCivicoBarra() != null)
				return false;
		} else if (!indirizzoSoccorsoCivicoBarra.equals(other.getIndirizzoSoccorsoCivicoBarra()))
			return false;
		if (indirizzoSoccorsoTipoVia == null) {
			if (other.getIndirizzoSoccorsoTipoVia() != null)
				return false;
		} else if (!indirizzoSoccorsoTipoVia.equals(other.getIndirizzoSoccorsoTipoVia()))
			return false;
		if (indirizzoSoccorsoVia == null) {
			if (other.getIndirizzoSoccorsoVia() != null)
				return false;
		} else if (!indirizzoSoccorsoVia.equals(other.getIndirizzoSoccorsoVia()))
			return false;
		if (indirizzoSoccorsoViaIncrocio == null) {
			if (other.getIndirizzoSoccorsoViaIncrocio() != null)
				return false;
		} else if (!indirizzoSoccorsoViaIncrocio.equals(other.getIndirizzoSoccorsoViaIncrocio()))
			return false;
		if (inizioMissione == null) {
			if (other.getInizioMissione() != null)
				return false;
		} else if (!inizioMissione.equals(other.getInizioMissione()))
			return false;
		if (inizioRientro == null) {
			if (other.getInizioRientro() != null)
				return false;
		} else if (!inizioRientro.equals(other.getInizioRientro()))
			return false;
		if (luogoDestinazioneMissione == null) {
			if (other.getLuogoDestinazioneMissione() != null)
				return false;
		} else if (!luogoDestinazioneMissione.equals(other.getLuogoDestinazioneMissione()))
			return false;
		if (membri == null) {
			if (other.getMembri() != null)
				return false;
		} else if (!membri.equals(other.getMembri()))
			return false;
		if (motivoChiamata == null) {
			if (other.getMotivoChiamata() != null)
				return false;
		} else if (!motivoChiamata.equals(other.getMotivoChiamata()))
			return false;
		if (motivoChiamataDettaglio == null) {
			if (other.getMotivoChiamataDettaglio() != null)
				return false;
		} else if (!motivoChiamataDettaglio.equals(other.getMotivoChiamataDettaglio()))
			return false;
		if (partenzaMezzo == null) {
			if (other.getPartenzaMezzo() != null)
				return false;
		} else if (!partenzaMezzo.equals(other.getPartenzaMezzo()))
			return false;
		if (partenzaMissioneLocalita == null) {
			if (other.getPartenzaMissioneLocalita() != null)
				return false;
		} else if (!partenzaMissioneLocalita.equals(other.getPartenzaMissioneLocalita()))
			return false;
		if (partenzaMissioneVia == null) {
			if (other.getPartenzaMissioneVia() != null)
				return false;
		} else if (!partenzaMissioneVia.equals(other.getPartenzaMissioneVia()))
			return false;
		if (pazienti == null) {
			if (other.getPazienti() != null)
				return false;
		} else if (!pazienti.equals(other.getPazienti()))
			return false;
		if (riferimenti == null) {
			if (other.getRiferimenti() != null)
				return false;
		} else if (!riferimenti.equals(other.getRiferimenti()))
			return false;
		if (stazionamentoFineMissione == null) {
			if (other.getStazionamentoFineMissione() != null)
				return false;
		} else if (!stazionamentoFineMissione.equals(other.getStazionamentoFineMissione()))
			return false;
		if (stazionamentoFineMissionePosMezzo == null) {
			if (other.getStazionamentoFineMissionePosMezzo() != null)
				return false;
		} else if (!stazionamentoFineMissionePosMezzo.equals(other.getStazionamentoFineMissionePosMezzo()))
			return false;
		if (totKMPercorsi != other.getTotKMPercorsi())
			return false;
		if (tratte == null) {
			if (other.getTratte() != null)
				return false;
		} else if (!tratte.equals(other.getTratte()))
			return false;
		return true;
	}
	
	
	
}
