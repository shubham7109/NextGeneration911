package person;

import java.util.List;

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
	
	@RequestMapping("/persons")
	public List<Person> getAllPersons() {
		return personService.getAllPersons();
	}
	
	@RequestMapping("/persons/{id}")
	public Person getPerson(@PathVariable("id") int id) {
		return personService.getPerson(id);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/persons")
	public void addPerson(@RequestBody Person person) {
		personService.addPerson(person);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/persons/{id}")
	public void updatePerson(@RequestBody Person person, @PathVariable("id") int id) {
		personService.updatePerson(id, person);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/persons/{id}")
	public void deletePerson(@PathVariable("id") int id) {
		personService.deletePerson(id);
	}
}
