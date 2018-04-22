package app.logs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class LogsReverseComparator implements Comparator<Logs> {
	
	public int compare(Logs a, Logs b) {
		
		SimpleDateFormat f = new SimpleDateFormat("MMM-dd-yy");
		Date da = new Date();
		try { da = f.parse(a.getDate());} catch (ParseException e) {}
		Date db = new Date();
		try { db = f.parse(a.getDate());} catch (ParseException e) {}
		
		if (db.equals(da)) {
			SimpleDateFormat g = new SimpleDateFormat("HH:mm");
			Date ta = new Date();
			try { ta = g.parse(a.getTime());} catch (ParseException e) {}
			Date tb = new Date();
			try { ta = g.parse(a.getTime());} catch (ParseException e) {}
			
			return ta.compareTo(tb) * -1;
		}
		
		return da.compareTo(db) * -1;
	}
	
}
