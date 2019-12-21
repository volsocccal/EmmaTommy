package emmaTommy.DataModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date> {
 
    private static final String DATE_FORMAT_STRING = "yyyy-MM-dd";
 
    @Override
    public String marshal(Date date) {
    	String date_str;
    	if (date == null) {
    		date_str =  "";
    	} else {
    		date_str = (new SimpleDateFormat(DATE_FORMAT_STRING)).format(date);
    	}
        return date_str;
    }
 
    @Override
    public Date unmarshal(String v) {   
    	Date d = null;
        try {
			d = new SimpleDateFormat(DATE_FORMAT_STRING).parse(v);
		} catch (ParseException e) {
		}
        return d;
    }
 
}