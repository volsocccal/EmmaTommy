package emmaTommy.DataModel;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import emmaTommy.DataModel.Missione;
 
@XmlRootElement(name = "missioni")
@XmlAccessorType (XmlAccessType.FIELD)
public class Missioni extends toDataFormatClass {
	
	/** Empty Constructor */
	public Missioni() {super();}
	
	/** Missioni Annotated List */
    @XmlElement(name = "missione")
    protected List<Missione> missioni = null; 
    public List<Missione> getMissioni() {
        return this.missioni;
    } 
    public void setMissioni(List<Missione> missioni) {
        this.missioni = missioni;
    }
    
    @Override
	public String toString() {
		String str = "Missioni [";
		for (Missione m: this.missioni) {
			str += m.toString();
		}
		str += "]";
		return str;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((missioni == null) ? 0 : missioni.hashCode());
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
		Missioni other = (Missioni) obj;
		if (missioni == null) {
			if (other.missioni != null)
				return false;
		} else if (!missioni.equals(other.missioni))
			return false;
		return true;
	}
    
    
    
}
