package app.instant_message;

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
		// let operators be a list of all operators
		List<Operators> operators = operatorsService.getAllOperators();
		
		/*
		let hist be a list of logs, sorted in reverse chronological order where logs.date = today's date
		
		if there is at least 1 operators who has not received a call today
			then return the id of the first operator in operators who has not received a call today and whose status == 0 and whose accessibility != 0
		
		else
			return the id of the first operator in hist whose status == 0 and whose accessibility != 0
			
		*/
		
		
		for (int i = 0; i < operators.size(); i++) {
			if (operators.get(i).getStatus() == 0) {
				return operators.get(i);
			}
		}
		//TODO: Replace with Operators Table
		return new Operators();
	}

}
