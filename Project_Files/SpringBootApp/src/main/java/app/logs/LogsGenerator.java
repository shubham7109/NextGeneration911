package app.logs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

public class LogsGenerator {
	
	@Autowired
	private LogsService logsService;
	
	public static void main(String[] args) {
		Random rand = new Random(System.currentTimeMillis());
		
		List<String> operators = new ArrayList<String>();
		operators.add("Shubham Sharma");
		operators.add("Mike Onyszczak");
		operators.add("Paul Biermann");
		operators.add("Rishab Narendra");
		operators.add("Jennifer Hardy");
		operators.add("Sam Burns");
		
		List<String> phoneNumbers = new ArrayList<String>();
		phoneNumbers.add("515-298-5098");
		phoneNumbers.add("224-330-0987");
		phoneNumbers.add("515-692-2253");
		phoneNumbers.add("555-685-1238");
		phoneNumbers.add("515-244-9824");
		phoneNumbers.add("515-900-5955");
		phoneNumbers.add("515-254-4449");
		phoneNumbers.add("546-549-7648");
		phoneNumbers.add("847-943-7754");
		
		List<Logs> logs = new ArrayList<Logs>();
		
		for (int i = 0; i < 300; i++) {
			Logs l = new Logs();
			
			l.setCallLength(Integer.toString((rand.nextInt(309) + 6)));
			l.setDate("Apr-" + Integer.toString(rand.nextInt(21) + 1) + "-18");
			l.setID(Integer.toString(rand.nextInt(1000000) + 1));
			l.setOperatorName(operators.get(rand.nextInt(operators.size())));
			l.setPhoneNumber(phoneNumbers.get(rand.nextInt(phoneNumbers.size())));
			l.setTime(Integer.toString(rand.nextInt(23)) + ":" + Integer.toString(rand.nextInt(59)));
			
			logs.add(l);
			/*
			logsService.addLogs(l);
		*/}
		
		
		
	}

}
