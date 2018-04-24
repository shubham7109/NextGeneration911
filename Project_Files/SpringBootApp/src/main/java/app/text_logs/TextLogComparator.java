package app.text_logs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class TextLogComparator implements Comparator<TextLog> {

	@Override
	public int compare(TextLog a, TextLog b) {
		
		SimpleDateFormat f = new SimpleDateFormat("HH:mm MMM-dd-yy");
		
		Date da = new Date();
		try {da = f.parse(a.getTimestamp());} catch (ParseException e) {}
		Date db = new Date();
		try {db = f.parse(b.getTimestamp());} catch (ParseException e) {}
		
		return da.compareTo(db);
	}
	

}
