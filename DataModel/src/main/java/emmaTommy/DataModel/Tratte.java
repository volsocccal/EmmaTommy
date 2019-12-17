package emmaTommy.DataModel;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import emmaTommy.DataModel.Tratta;
 
@XmlRootElement(name = "tratte")
@XmlAccessorType (XmlAccessType.FIELD)
public class Tratte  extends toDataFormatClass {
	
	/** Empty Constructor */
	public Tratte() {super();}
	
	/** Tratte Annotated List */
    @XmlElement(name = "tratta")
    protected List<Tratta> tratte = null; 
    public List<Tratta> getTratte() {
        return this.tratte;
    } 
    public void setTratte(List<Tratta> tratte) {
        this.tratte = tratte;
    }
    
    @Override
	public String toString() {
		String str = "Tratte [";
		for (Tratta t: this.tratte) {
			str += t.toString();
		}
		str += "]";
		return str;
	}
    
}