package com.example.demo;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class LogsService {
	
	private List<Logs> logs = new ArrayList<>(Arrays.asList(
			new Logs("01/01/1998","10:56","16","George Bush","515-987-6543"),
			new Logs("02/02/2018","11:01","10","Donald Trump","765-765-7654"),
			new Logs("03/03/1666","12:54","76","George Washington","987-876-7654"),
			new Logs("01/01/1998","10:56","16","George Bush","515-987-6543"),
			new Logs("02/02/2018","11:01","10","Donald Trump","765-765-7654"),
			new Logs("03/03/1666","12:54","76","George Washington","987-876-7654"),
			new Logs("01/01/1998","10:56","16","George Bush","515-987-6543"),
			new Logs("02/02/2018","11:01","10","Donald Trump","765-765-7654"),
			new Logs("03/03/1666","12:54","76","George Washington","987-876-7654"),
			new Logs("01/01/1998","10:56","16","George Bush","515-987-6543"),
			new Logs("02/02/2018","11:01","10","Donald Trump","765-765-7654"),
			new Logs("03/03/1666","12:54","76","George Washington","987-876-7654")
			));
	
	public List<Logs> getAllOperators() {
		return logs;
	}
	
// 	To be implemented for future build of app
//	public void addOperator(Logs operators) {
//		logs.add(operators);
//	}
}

