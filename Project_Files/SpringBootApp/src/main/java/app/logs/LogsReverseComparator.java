package app.logs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class LogsReverseComparator implements Comparator<Logs> {
	
	public int compare(Logs a, Logs b) {
		
		LogsComparator c = new LogsComparator();
		return -1 * c.compare(a, b);

	}
	
}
