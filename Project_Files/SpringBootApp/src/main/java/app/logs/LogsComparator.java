package app.logs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;



public class LogsComparator implements Comparator<Logs> {
	
	final private String dpe = "Date Parse Error";
	
	public int compare(Logs a, Logs b) {
		
		SimpleDateFormat f = new SimpleDateFormat("MMM-dd-yy");
		Date da = new Date();
		try { da = f.parse(a.getDate());} catch (ParseException e) {System.out.println(dpe);}
		Date db = new Date();
		try { db = f.parse(b.getDate());} catch (ParseException e) {System.out.println(dpe);}
		
		if (db.equals(da)) {
			SimpleDateFormat g = new SimpleDateFormat("HH:mm");
			Date ta = new Date();
			try { ta = g.parse(a.getTime());} catch (ParseException e) {System.out.println(dpe);}
			Date tb = new Date();
			try { tb = g.parse(b.getTime());} catch (ParseException e) {System.out.println(dpe);}
			
			return ta.compareTo(tb);
		}
		
		return da.compareTo(db);
	}
}