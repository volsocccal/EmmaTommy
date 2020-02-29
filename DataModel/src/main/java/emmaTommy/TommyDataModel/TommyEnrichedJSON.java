package emmaTommy.TommyDataModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.logging.log4j.LogManager;


@Entity
@Table(name="servizi")
public class TommyEnrichedJSON {

	static org.apache.logging.log4j.Logger logger = LogManager.getLogger("TommyDataModel");
	
	/** JSON Service Field */
	protected String jsonServizio;
	public String getJsonServizio() {
		return this.jsonServizio;	
	}
	public void setJsonServizio(String jsonServizio) {
		String method_name = "::setJsonServizio(): ";
		if (jsonServizio == null) {
			throw new NullPointerException(method_name + "Input Servizio was nullptr");
		}
		this.jsonServizio = jsonServizio;
	}
	
	/** Number of KM for the Servizio */
	protected int km;
	public int getKm() {
		return this.km;
	}
	public void setKm(int km) {
		String method_name = "::setKm(): ";
		if (km < 0) {
			throw new IllegalArgumentException(method_name + "Input Servizio was nullptr");
		}
		this.km = km;
	}
	
	/** Servizio ID */
	@Id
	@Column(name="codice_servizio")
	protected String codiceServizio;
	public String getCodiceServizio() {
		return this.codiceServizio;
	}
	public void setCodiceServizio(String codiceServizio) {
		this.codiceServizio = codiceServizio;
	}
	
	/** Mezzo ID */
	@Column(name="codice_mezzo")
	protected String codiceMezzo;
	public String getCodiceMezzo() {
		return this.codiceMezzo;
	}
	public void setCodiceMezzo(String codiceMezzo) {
		this.codiceMezzo = codiceMezzo;
	}
	
	/** Initialized Flag */
	protected Boolean initializedFlag; 
	public Boolean isInitialized() {
		return this.initializedFlag;
	}
	
	public TommyEnrichedJSON() {
		this.jsonServizio = null;
		this.codiceMezzo = null;
		this.codiceServizio = null;
		this.km = -1;
		this.initializedFlag = false;
	}
	
	public TommyEnrichedJSON (String jsonServizio) {
		this();
		this.setJsonServizio(jsonServizio);
	}

	

	

	

	
	
}
