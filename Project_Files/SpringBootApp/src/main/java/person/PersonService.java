package person;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository personRepository;
	
	/*
	private List<Person> persons = new ArrayList<>(Arrays.asList(
			new Person(123),
			new Person(456),
			new Person(789)
			));
	*/
	
	public List<Person> getAllPersons() {
		//return persons;
		List<Person> persons = new ArrayList<>();
		personRepository.findAll().forEach(persons::add);
		return persons;
	}
	
	public Person getPerson(String id) {
		//return new Person(id);
		return personRepository.findOne(id);
	}
	
	public void addPerson(Person person) {
		//persons.add(person);
		personRepository.save(person);
	}
	
	public void updatePerson(int id, Person person) {
		/* for (int i = 0; i < persons.size(); i++) {
			Person p = persons.get(i);
			if (p.getId() == id) {
				persons.set(i, person);
				return;
			}
		}*/
		personRepository.save(person);
	}
	
	public void deletePerson(String id) {
		/*for (int i = 0; i < persons.size(); i++) {
			Person p = persons.get(i);
			if (p.getId() == id) {
				persons.remove(i);
			}
		}*/
		personRepository.delete(id);
	}
}
