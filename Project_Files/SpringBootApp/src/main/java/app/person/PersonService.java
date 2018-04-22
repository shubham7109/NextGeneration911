package app.person;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository personRepository;
	
	/**
	 * @return a list of persons
	 */
	public List<Person> getAllPersons() {
		//return persons;
		List<Person> persons = new ArrayList<>();
		personRepository.findAll().forEach(persons::add);
		return persons;
	}
	
	/**
	 * @param id of person
	 * @return a person with the specified id
	 */
	public Person getPerson(String id) {
		//return new Person(id);
		return personRepository.findOne(id);
	}
	
	/**
	 * adds a person
	 * @param person to be added
	 */
	public void addPerson(Person person) {
		//persons.add(person);
		personRepository.save(person);
	}
	
	/**
	 * updates a person in the database
	 * @param id of person
	 * @param person person
	 */
	public void updatePerson(int id, Person person) {
		personRepository.save(person);
	}
	
	/**
	 * deletes a person
	 * @param id of person
	 */
	public void deletePerson(String id) {
		personRepository.delete(id);
	}
	
	private void save(Person person) {
		String s = person.getPicture();
		s = s.replace("\n", "").replace("\r", "");
		byte[] decoded = Base64.getDecoder().decode(s);
		FileOutputStream outs = null;
		try {
			outs = new FileOutputStream("user_images/" + new String(person.getId()) + ".jpg");
			outs.write(decoded);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		personRepository.save(person);
	}
}
