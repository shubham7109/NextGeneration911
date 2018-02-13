package person;

import org.springframework.stereotype.Service;

@Service
public class PersonService {
	
	
	public Person getPerson(String id) {
		//TODO retrieve a person from database
		return new Person(id, "testPerson");
	}
}
