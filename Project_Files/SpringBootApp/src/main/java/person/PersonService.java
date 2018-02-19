package person;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PersonService {
	
	private List<Person> persons = new ArrayList<>(Arrays.asList(
			new Person(123,"","","lol","","","","","",50010,"","","","",162,75),
			new Person(456,"","","","","","","","",50111,"","","","",162,75),
			new Person(789,"","","","","","","","",44444,"","","","",162,75)
			));
	
	public List<Person> getAllPersons() {
		return persons;
	}
	
	public Person getPerson(int id) {
		for(int i=0; i<persons.size(); i++){
			if(persons.get(i).getId() == id)
				return persons.get(i);
		}

		return new Person(id);
	}
	
	public void addPerson(Person person) {
		persons.add(person);
	}
	
	public void updatePerson(int id, Person person) {
		for (int i = 0; i < persons.size(); i++) {
			Person p = persons.get(i);
			if (p.getId() == id) {
				persons.set(i, person);
				return;
			}
		}
	}
	
	public void deletePerson(int id) {
		for (int i = 0; i < persons.size(); i++) {
			Person p = persons.get(i);
			if (p.getId() == id) {
				persons.remove(i);
			}
		}
	}
}
