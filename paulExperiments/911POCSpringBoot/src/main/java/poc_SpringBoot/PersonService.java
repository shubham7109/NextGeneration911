package poc_SpringBoot;

import org.springframework.stereotype.Service;

@Service
public class PersonService {
	
	
	public Person getPerson(String id) {
		return new Person(id, "testPerson");
	}
}
