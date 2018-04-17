package app.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	
	/**
	 * @return a list of persons
	 */
	@RequestMapping("/persons")
	public Iterable<Person> getAllPersons() {
		return personService.getAllPersons();
	}
	
	/**
	 * @param id of person
	 * @return a person with the given id
	 */
	@RequestMapping("/persons/{id}")
	public Person getPerson(@PathVariable("id") String id) {
		return personService.getPerson(id);
	}
	
	/**
	 * adds a new person to the database
	 * @param person 
	 */
	@RequestMapping(method=RequestMethod.POST, value="/persons")
	public void addPerson(@RequestBody Person person) {
		personService.addPerson(person);
	}
	
	/**
	 * updates a person in the database
	 * @param person person obeject
	 * @param id id of person
	 */
	@RequestMapping(method=RequestMethod.PUT, value="/persons/{id}")
	public void updatePerson(@RequestBody Person person, @PathVariable("id") int id) {
		personService.updatePerson(id, person);
	}
	
	/**
	 * deletes a person in the database
	 * @param id of person
	 */
	@RequestMapping(method=RequestMethod.DELETE, value="/persons/{id}")
	public void deletePerson(@PathVariable("id") String id) {
		personService.deletePerson(id);
	}
}
