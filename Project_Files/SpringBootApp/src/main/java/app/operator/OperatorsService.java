package app.operator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperatorsService {
	
	@Autowired
	private OperatorsRepository operatorsRepository;

	/**
	 * @return list of operators
	 */
	public List<Operators> getAllOperators() {
		List<Operators> operators = new ArrayList<>();
		operatorsRepository.findAll().forEach(operators::add);
		return operators;
	}

	/**
	 * @param id of operator
	 * @return operator
	 */
	public Operators getOperator(String id) {
		return operatorsRepository.findOne(id);
	}

	/**
	 * adds a operator to the database
	 * @param operators Operators
	 */
	public void addOperator(Operators operators) {
		operatorsRepository.save(operators);
	}

	/**
	 * updates a operator in the database
	 * @param id of operator
	 * @param operators the Operator
	 */
	public void updateOperator(int id, Operators operators) {
		operatorsRepository.save(operators);
	}

	/**
	 * deletes a operator
	 * @param id of operator
	 */
	public void deleteOperator(String id) {
		operatorsRepository.delete(id);
	}
	
	public boolean checkPassword(String userName, String password)
	{
		List<Operators> login;
		login = getAllOperators();
		for(int i = 0; i < login.size(); i++)
			if(login.get(i).getUserName().equals(userName) && login.get(i).getPassword().equals(password))
				return true;
		return false;
	}
}
