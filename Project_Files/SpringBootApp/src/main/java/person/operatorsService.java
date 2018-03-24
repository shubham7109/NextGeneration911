package person;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class operatorsService {
	
	@Autowired
	private OperatorsRepository operatorsRepository;
	
	/*
	private List<Operators> operator = new ArrayList<>(Arrays.asList(
			new Operators(123, "shubham", "password", "Shubham", "Sharma", 0),
			new Operators(456, "paul", "password1", "Paul", "Biermann", 1),
			new Operators(789, "mike", "password2", "Michael", "Onyszczak", 2)
			));
			*/
	
	public List<Operators> getAllOperators() {
		List<Operators> operators = new ArrayList<>();
		operatorsRepository.findAll().forEach(operators::add);
		return operators;
	}
	
	public Operators getOperator(String id) {
		/*for(int i = 0; i < operator.size(); i++)
		{
			if(operator.get(i).getID() == id)
				return operator.get(i); 
		}
		return new Operators(id);*/
		return operatorsRepository.findOne(id);
	}
	
	public void addOperator(Operators operators) {
		/*operator.add(operators);*/
		operatorsRepository.save(operators);
	}
	
	public void updateOperator(int id, Operators operators) {
		/*for (int i = 0; i < operator.size(); i++) {
			Operators o = operator.get(i);
			if (o.getID() == id) {
				operator.set(i, operators);
				return;
			}
		}*/
		operatorsRepository.save(operators);
	}
	
	public void deleteOperator(String id) {
		/*for (int i = 0; i < operator.size(); i++) {
			Operators o = operator.get(i);
			if (o.getID() == id) {
				operator.remove(i);
			}
		}*/
		operatorsRepository.delete(id);
	}
}
