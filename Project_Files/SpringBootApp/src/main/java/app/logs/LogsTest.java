package app.logs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class LogsTest {

	public static void main(String[] args) {
		
		Logs a = new Logs();
		a.setDate("Apr-20-18");
		a.setTime("20:16");
		a.setOperatorName("a");
		
		Logs b = new Logs();
		b.setDate("Apr-20-18");
		b.setTime("10:16");
		b.setOperatorName("b");
		
		List<Logs> logs = new ArrayList<Logs>();
		logs.add(a);
		logs.add(b);
		
		LogsComparator lc = new LogsComparator();
		LogsReverseComparator rlc = new LogsReverseComparator();
		
		System.out.println(lc.compare(a, b));
		System.out.println(rlc.compare(a, b));
		
		
		System.out.println(logs.toString());
		
		logs.sort(new LogsComparator());
		System.out.println(logs.toString());
		
		Collections.reverse(logs);
		
		System.out.println(logs.toString());
	}

}
