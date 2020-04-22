package emmaTommy.DataModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapterYYMMDDdash extends XmlAdapter<String, LocalDate> {
 
    private static final String DATE_FORMAT_STRING = "yyyy-MM-dd";
 
    @Override
    public String marshal(LocalDate date) {
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
    public LocalDate unmarshal(String v) {   
    	LocalDate d = null;
        try {
        	DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT_STRING); 
        	d = LocalDate.parse(v, dtf);
		} catch (java.time.format.DateTimeParseException e) {
		}
        return d;
    }
 
}