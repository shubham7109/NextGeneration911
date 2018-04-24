package app.logs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestSorting {

	public static void main(String[] args) {
		
		List<Logs> logs = initializeLogs();
		System.out.println("Starting Order: " + logs.toString());		
		
		logs.sort(new LogsComparator());
		System.out.println("Sorted Order: " + logs.toString());
		
		Collections.reverse(logs);
		System.out.println("Reverse Sorted Order: " + logs.toString());
		
		

	}
	
	private static List<Logs> initializeLogs() {
		List<Logs> logs = new ArrayList<Logs>();
		
		Logs a = new Logs();
		a.setOperatorName("A");
		a.setDate("Apr-02-18");
		a.setTime("5:30");
		logs.add(a);
		
		Logs b = new Logs();
		b.setOperatorName("B");
		b.setDate("Apr-04-18");
		b.setTime("6:30");
		logs.add(b);
		
		Logs c = new Logs();
		c.setOperatorName("C");
		c.setDate("Apr-02-18");
		c.setTime("15:30");
		logs.add(c);
		
		Logs d = new Logs();
		d.setOperatorName("D");
		d.setDate("Apr-04-18");
		d.setTime("16:30");
		logs.add(d);
		
		return logs;
	}

}
