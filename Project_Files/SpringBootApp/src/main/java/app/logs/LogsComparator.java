package app.logs;

import java.util.Comparator;

public class LogsComparator implements Comparator<Logs> {
	
	public int compare(Logs a, Logs b) {
		
		if (a.getDate().compareTo(b.getDate()) < 0) {
			return -1;
		} else if (a.getDate().compareTo(b.getDate()) > 0) {
			return 1;
		} else {
			if (a.getTime().compareTo(b.getTime()) < 0 ) {
				return -1;
			} else if (a.getTime().compareTo(b.getTime()) > 0) {
				return 1;
			}
		}
		
		return 0;
	}
	
}