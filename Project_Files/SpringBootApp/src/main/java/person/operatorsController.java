package person;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class operatorsController {

	@Autowired
	private operatorsService operatorService;

	/**
	 * @return list of all operators
	 */
	@RequestMapping("/operators")
	public Iterable<Operators> getAllOperators()
	{
		return operatorService.getAllOperators();
	}

	/**
	 * @param id of Operators
	 * @return operator with the given id or null if the id doesn't exist
	 */
	@RequestMapping("/operators/{id}")
	public Operators getOperators(@PathVariable("id") String id) 
	{
		return operatorService.getOperator(id);
	}

	/**
	 * adds a operator to the database
	 * @param Operators Operators object
	 */
	@RequestMapping(method=RequestMethod.POST, value="/operators")
	public void addOperators(@RequestBody Operators Operators) 
	{
		operatorService.addOperator(Operators);
	}

	/**
	 * Updates a operator in the database
	 * @param Operators Operators object
	 * @param id of Operators
	 */
	@RequestMapping(method=RequestMethod.PUT, value="/operators/{id}")
	public void updateOperators(@RequestBody Operators Operators, @PathVariable("id") int id) 
	{
		operatorService.updateOperator(id, Operators);
	}

	/**
	 * Deletes a operator in the database
	 * @param id of Operators
	 */
	@RequestMapping(method=RequestMethod.DELETE, value="/operators/{id}")
	public void deleteOperators(@PathVariable("id") String id)
	{
		operatorService.deleteOperator(id);
	}

}
