package person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	@RequestMapping("/persons/{id}")
	public Person getPerson(@PathVariable("id") String id) {
		return personService.getPerson(id);
	}
	
}
