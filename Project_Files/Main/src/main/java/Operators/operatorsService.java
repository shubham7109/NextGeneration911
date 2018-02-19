package Operators;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class operatorsService {
	
	private List<Operators> operator = new ArrayList<>(Arrays.asList(
			new Operators(123),
			new Operators(456),
			new Operators(789)
			));
	
	public List<Operators> getAllOperators() {
		return operator;
	}
	
	public Operators getOperator(int id) {
		return new Operators(id);
	}
	
	public void addOperator(Operators operators) {
		operator.add(operators);
	}
	
	public void updateOperator(int id, Operators operators) {
		for (int i = 0; i < operator.size(); i++) {
			Operators o = operator.get(i);
			if (o.getID() == id) {
				operator.set(i, operators);
				return;
			}
		}
	}
	
	public void deleteOperator(int id) {
		for (int i = 0; i < operator.size(); i++) {
			Operators o = operator.get(i);
			if (o.getID() == id) {
				operator.remove(i);
			}
		}
	}
}
