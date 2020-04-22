package emmaTommy.DataModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateTimeAdapterYYYYMMDDTHHmmssZ extends XmlAdapter<String, LocalDateTime> {
 
    private static final String DATE_FORMAT_STRING = "yyyy-MM-dd'T'HH:mm:ss'Z'";
 
    @Override
    public String marshal(LocalDateTime date) {
    	String date_str;
    	if (date == null) {
    		date_str =  "";
    	} else {
    		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT_STRING); 
    		date_str = dtf.format(date);
    	}
        return date_str;
    }
 
    @Override
    public LocalDateTime unmarshal(String v) {   
    	LocalDateTime d = null;
        try {
        	DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT_STRING); 
        	d = LocalDateTime.parse(v, dtf);
		} catch (java.time.format.DateTimeParseException e) {
		}
        return d;
    }
 
}