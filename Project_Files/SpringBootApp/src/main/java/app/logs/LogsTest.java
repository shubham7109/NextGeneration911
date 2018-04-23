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
		
		Logs c = new Logs();
		c.setDate("Apr-18-18");
		c.setTime("15:28");
		c.setOperatorName("c");
		
		List<Logs> logs = new ArrayList<Logs>();
		logs.add(a);
		logs.add(b);
		logs.add(c);
		
		LogsComparator lc = new LogsComparator();
		LogsReverseComparator rlc = new LogsReverseComparator();
		
		logs.sort(lc);
		System.out.println(logs.toString());
		
		Collections.reverse(logs);
		
		System.out.println(logs.toString());
	}

}
