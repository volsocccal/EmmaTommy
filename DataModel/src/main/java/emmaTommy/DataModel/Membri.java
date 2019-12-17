package emmaTommy.DataModel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import emmaTommy.DataModel.Membro;
 
@XmlRootElement(name = "membri")
@XmlAccessorType (XmlAccessType.FIELD)
public class Membri extends toDataFormatClass
{
	
	/** Empty Constructor */
	public Membri() {super(); this.membri = new ArrayList<Membro>();}
	
	/** type Attribute */
	@XmlAttribute(name = "type")
	protected String type = "array";
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return this.type;
	}
	
	/** Membri Annotated List */
    @XmlElement(name = "membro")
    protected List<Membro> membri = null; 
    public List<Membro> getMembri() {
        return this.membri;
    } 
    public void setMembri(List<Membro> membri) {
        this.membri = membri;
    }
    
    @Override
	public String toString() {
		String str = "Membri [";
		for (Membro m: this.membri) {
			str += m.toString();
		}
		str += "]";
		return str;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((membri == null) ? 0 : membri.hashCode());
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
		Membri other = (Membri) obj;
		if (membri == null) {
			if (other.membri != null)
				return false;
		} else if (!membri.equals(other.membri))
			return false;
		return true;
	}
    
    
    
}