package emmaTommy.DataModel;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateTimeAdapterHHmm extends XmlAdapter<String, LocalTime> {
 
    private static final String DATE_FORMAT_STRING = "HH:mm";
 
    @Override
    public String marshal(LocalTime date) {
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
    public LocalTime unmarshal(String v) {   
    	LocalTime d = null;
        try {
        	DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT_STRING); 
        	d = LocalTime.parse(v, dtf);
		} catch (java.time.format.DateTimeParseException e) {
		}
        return d;
    }
 
}