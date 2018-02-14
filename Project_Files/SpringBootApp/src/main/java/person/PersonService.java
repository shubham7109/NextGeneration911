package person;

import org.springframework.stereotype.Service;

@Service
public class PersonService {
	
	
	public Person getPerson(int id) {
		//TODO retrieve a person from database
		return new Person(id);
	}
}
