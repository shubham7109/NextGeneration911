package app.instant_message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.operator.*;
import app.logs.*;

@RestController
public class InstantMessageRouter {
	
	@Autowired
	private OperatorsService operatorsService;
	
	@Autowired
	private LogsService logsService;
	
	
	/**
	 * @return an available operator
	 */
	@RequestMapping("/makecall")
	public String makeCall() {
		Operators oper = getAvailableOperator();
		return oper.getId();
	}
	
	/**
	 * @param id of the person who is calling
	 * @return an available operator
	 */
	@RequestMapping("/makecall/{id}")
	public String makeIdCall(@PathVariable("id") String id) {
		Operators oper = getAvailableOperator();
		return oper.getId();
	}
	
	/* return the first available operator whose status is 0 (who is available) */
	private Operators getAvailableOperator() {
		
		// let availOper be a list of all available operators
		List<Operators> operators = operatorsService.getAllOperators();
		List<Operators> availOper = new ArrayList<Operators>();
		for (int i = 0; i < operators.size(); i++) {
			if (operators.get(i).getStatus() == 0 && operators.get(i).getAccesibility() != 0) {
				availOper.add(operators.get(i));
			}
		}
		
		//let todayLogs be a list of each operator's most recent call log, sorted in chronological order
		List<Logs> logs = logsService.getAllLogs();
		List<Logs> latestOpLog = new ArrayList<Logs>();
		
		SimpleDateFormat f = new SimpleDateFormat("MMM-dd-yy");
		
		for (int i = 0; i < logs.size() && i < 1000; i++) {
			Logs l = logs.get(i);
			String ln = l.getOperatorName();
			
			boolean InLatestOpLog = false;
			for (int j = 0; j < latestOpLog.size(); j++) {
				if (ln.equals(latestOpLog.get(j).getOperatorName())) {
					InLatestOpLog = true;
				}
			}
			
			boolean inAvailOps = false;
			for (int j = 0; j < availOper.size(); j++) {
				Operators op = availOper.get(j);
				if (ln.equals(op.getFirstName() + " " + op.getLastName())) {
					inAvailOps = true;
				}
			}
			
			if (!InLatestOpLog && inAvailOps) {
				latestOpLog.add(l);
			}
		}
		
		latestOpLog.sort(new LogsComparator());
		
		
		if (!availOper.isEmpty()) {
			//if an operator has not yet received a call today, then return that operator
			//		let called be an array of ints such that:
			//			iff availOper.get(i) has received a call today, then called[i] = true
			boolean[] called = new boolean[availOper.size()];
			for (int i = 0; i < availOper.size(); i++) {
				called[i] = false;
			}
			for (int i = 0; i < logs.size(); i++) {
				for (int j = 0; j < availOper.size(); j++) {
					if ( logs.get(i).getOperatorName() == availOper.get(j).getFirstName() + " " + availOper.get(j).getLastName()) {
						called[j] = true;
					}
				}
			}
			
			for (int i = 0; i < called.length; i++) {
				if (called[i] = false) {
					return availOper.get(i);
				}
			}
			
			//return the operator who appears first on the sorted
			for ( int i = 0; i < availOper.size(); i++) {
				if (latestOpLog.get(0).getOperatorName().equals(availOper.get(i).getFirstName() + " " + availOper.get(i).getLastName())) {
					return availOper.get(i);
				}
			}
			
			
		}
		
		return new Operators();
		
		/*
		=== Old Code ===
		for (int i = 0; i < operators.size(); i++) {
			if (operators.get(i).getStatus() == 0) {
				return operators.get(i);
			}
		}
		//TODO: Replace with Operators Table
		return new Operators();
		*/
	}

}
