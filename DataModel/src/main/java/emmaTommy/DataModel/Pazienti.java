package emmaTommy.DataModel;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import emmaTommy.DataModel.Paziente;
 
@XmlRootElement(name = "pazienti")
@XmlAccessorType (XmlAccessType.FIELD)
public class Pazienti extends toDataFormatClass {
	
	/** Empty Constructor */
	public Pazienti() {super();}
	
	/** Pazienti Annotated List */
    @XmlElement(name = "paziente")
    protected List<Paziente> pazienti = null; 
    public List<Paziente> getPazienti() {
        return this.pazienti;
    } 
    public void setPazienti(List<Paziente> pazienti) {
        this.pazienti = pazienti;
    }
    
	@Override
	public String toString() {
		String str = "Pazienti [";
		for (Paziente p: this.pazienti) {
			str += p.toString();
		}
		str += "]";
		return str;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pazienti == null) ? 0 : pazienti.hashCode());
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
		Pazienti other = (Pazienti) obj;
		if (pazienti == null) {
			if (other.pazienti != null)
				return false;
		} else if (!pazienti.equals(other.pazienti))
			return false;
		return true;
	}
    
    
    
}