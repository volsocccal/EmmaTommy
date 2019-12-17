package emmaTommy.DataModel;

import java.util.Date;
import javax.xml.bind.annotation.*;

import emmaTommy.DataModel.Membri;
import emmaTommy.DataModel.Pazienti;
import emmaTommy.DataModel.Tratte;

@XmlRootElement(name = "missione")
@XmlAccessorType(XmlAccessType.FIELD)
public class Missione extends toDataFormatClass {

	/** Empty Constructor */
	public Missione () {super();}
	
	/** Elenco Delle Tratte della Missione */
	protected Tratte tratte = null;
	@XmlElement(name = "tratte", required = true)	
	public void setTratte(Tratte tratte) {
		this.tratte = tratte;
	}
	public Tratte getTratte() {
		return this.tratte;
	}
	
	/** Elenco Dei Membri dell'Equipaggio della Missione */
	protected Membri membri = null;
	@XmlElement(name = "membri", required = true)	
	public void setMembri(Membri membri) {
		this.membri = membri;
	}
	public Membri getMembri() {
		return this.membri;
	}
	
	/** Elenco Dei Pazienti della Missione */
	protected Pazienti pazienti = null;
	@XmlElement(name = "pazienti", required = true)	
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
	@XmlElement(name = "mi-cd-mezzo>", required = true)	
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
	protected Date fineMissione;
	public void setFineMissione (Date fineMissione) {
		this.fineMissione = fineMissione;
	}
	public Date getFineMissione() {
		return this.fineMissione;
	}
	
	/** Data di Inizio Rientro */
	@XmlElement(name = "mi-dt-inizio-r", required = true)	
	protected Date inizioRientro;
	public void setInizioRientro(Date inizioRientro) {
		this.inizioRientro = inizioRientro;
	}
	public Date getInizioRientro() {
		return this.inizioRientro;
	}
	
	/** Data di Inizio Missione (Creazione Scheda) */
	@XmlElement(name = "mi-dt-missione", required = true)	
	protected Date inizioMissione;
	public void setInizioMissione(Date inizioMissione) {
		this.inizioMissione = inizioMissione;
	}
	public Date getInizioMissione() {
		return this.inizioMissione;
	}
	
	/** Data di Partenza del Mezzo */
	@XmlElement(name = "mi-dt-partenza", required = true)	
	protected Date partenzaMezzo;
	public void setPartenzaMezzo (Date partenzaMezzo) {
		this.partenzaMezzo = partenzaMezzo;
	}
	public Date getPartenzaMezzo() {
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
	@XmlElement(name = "me-sc-ds-comune-nome", required = true)	
	protected String comuneIntervento;
	public void setComuneIntervento(String comuneIntervento) {
		this.comuneIntervento = comuneIntervento;
	}
	public String getComuneIntervento() {
		return this.comuneIntervento;
	}
	
	/** Motivo della Chiamata */
	@XmlElement(name = "sc-ds-det-mot", required = true)	
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
	@XmlElement(name = "mi-ds-loc-part", required = true)	
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
				+ fineMissione + ", inizioRientro=" + inizioRientro + ", inizioMissione=" + inizioMissione
				+ ", partenzaMezzo=" + partenzaMezzo + ", codiceEvento=" + codiceEvento + ", codiceTrasporto="
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
			if (other.codiceEnte != null)
				return false;
		} else if (!codiceEnte.equals(other.codiceEnte))
			return false;
		if (codiceEvento == null) {
			if (other.codiceEvento != null)
				return false;
		} else if (!codiceEvento.equals(other.codiceEvento))
			return false;
		if (codiceMezzo == null) {
			if (other.codiceMezzo != null)
				return false;
		} else if (!codiceMezzo.equals(other.codiceMezzo))
			return false;
		if (codiceMissione != other.codiceMissione)
			return false;
		if (codiceMissionePrecedente != other.codiceMissionePrecedente)
			return false;
		if (codiceTrasporto == null) {
			if (other.codiceTrasporto != null)
				return false;
		} else if (!codiceTrasporto.equals(other.codiceTrasporto))
			return false;
		if (comuneIntervento == null) {
			if (other.comuneIntervento != null)
				return false;
		} else if (!comuneIntervento.equals(other.comuneIntervento))
			return false;
		if (convenzioneEnte == null) {
			if (other.convenzioneEnte != null)
				return false;
		} else if (!convenzioneEnte.equals(other.convenzioneEnte))
			return false;
		if (esitoMissione == null) {
			if (other.esitoMissione != null)
				return false;
		} else if (!esitoMissione.equals(other.esitoMissione))
			return false;
		if (fineMissione == null) {
			if (other.fineMissione != null)
				return false;
		} else if (!fineMissione.equals(other.fineMissione))
			return false;
		if (indirizzoSoccorsoCivico == null) {
			if (other.indirizzoSoccorsoCivico != null)
				return false;
		} else if (!indirizzoSoccorsoCivico.equals(other.indirizzoSoccorsoCivico))
			return false;
		if (indirizzoSoccorsoCivicoBarra == null) {
			if (other.indirizzoSoccorsoCivicoBarra != null)
				return false;
		} else if (!indirizzoSoccorsoCivicoBarra.equals(other.indirizzoSoccorsoCivicoBarra))
			return false;
		if (indirizzoSoccorsoTipoVia == null) {
			if (other.indirizzoSoccorsoTipoVia != null)
				return false;
		} else if (!indirizzoSoccorsoTipoVia.equals(other.indirizzoSoccorsoTipoVia))
			return false;
		if (indirizzoSoccorsoVia == null) {
			if (other.indirizzoSoccorsoVia != null)
				return false;
		} else if (!indirizzoSoccorsoVia.equals(other.indirizzoSoccorsoVia))
			return false;
		if (indirizzoSoccorsoViaIncrocio == null) {
			if (other.indirizzoSoccorsoViaIncrocio != null)
				return false;
		} else if (!indirizzoSoccorsoViaIncrocio.equals(other.indirizzoSoccorsoViaIncrocio))
			return false;
		if (inizioMissione == null) {
			if (other.inizioMissione != null)
				return false;
		} else if (!inizioMissione.equals(other.inizioMissione))
			return false;
		if (inizioRientro == null) {
			if (other.inizioRientro != null)
				return false;
		} else if (!inizioRientro.equals(other.inizioRientro))
			return false;
		if (luogoDestinazioneMissione == null) {
			if (other.luogoDestinazioneMissione != null)
				return false;
		} else if (!luogoDestinazioneMissione.equals(other.luogoDestinazioneMissione))
			return false;
		if (membri == null) {
			if (other.membri != null)
				return false;
		} else if (!membri.equals(other.membri))
			return false;
		if (motivoChiamata == null) {
			if (other.motivoChiamata != null)
				return false;
		} else if (!motivoChiamata.equals(other.motivoChiamata))
			return false;
		if (motivoChiamataDettaglio == null) {
			if (other.motivoChiamataDettaglio != null)
				return false;
		} else if (!motivoChiamataDettaglio.equals(other.motivoChiamataDettaglio))
			return false;
		if (partenzaMezzo == null) {
			if (other.partenzaMezzo != null)
				return false;
		} else if (!partenzaMezzo.equals(other.partenzaMezzo))
			return false;
		if (partenzaMissioneLocalita == null) {
			if (other.partenzaMissioneLocalita != null)
				return false;
		} else if (!partenzaMissioneLocalita.equals(other.partenzaMissioneLocalita))
			return false;
		if (partenzaMissioneVia == null) {
			if (other.partenzaMissioneVia != null)
				return false;
		} else if (!partenzaMissioneVia.equals(other.partenzaMissioneVia))
			return false;
		if (pazienti == null) {
			if (other.pazienti != null)
				return false;
		} else if (!pazienti.equals(other.pazienti))
			return false;
		if (riferimenti == null) {
			if (other.riferimenti != null)
				return false;
		} else if (!riferimenti.equals(other.riferimenti))
			return false;
		if (stazionamentoFineMissione == null) {
			if (other.stazionamentoFineMissione != null)
				return false;
		} else if (!stazionamentoFineMissione.equals(other.stazionamentoFineMissione))
			return false;
		if (stazionamentoFineMissionePosMezzo == null) {
			if (other.stazionamentoFineMissionePosMezzo != null)
				return false;
		} else if (!stazionamentoFineMissionePosMezzo.equals(other.stazionamentoFineMissionePosMezzo))
			return false;
		if (totKMPercorsi != other.totKMPercorsi)
			return false;
		if (tratte == null) {
			if (other.tratte != null)
				return false;
		} else if (!tratte.equals(other.tratte))
			return false;
		return true;
	}
	
	
	
}
