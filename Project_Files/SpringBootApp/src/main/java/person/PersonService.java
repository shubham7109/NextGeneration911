package person;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PersonService {
	
	public List<Person> getAllPersons() {
		return new ArrayList<>(Arrays.asList(
				new Person(123),
				new Person(456),
				new Person(789)
				));
	}
	
	public Person getPerson(int id) {
		return new Person(id);
	}
	
	
}
