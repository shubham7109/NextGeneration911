package poc_SpringBoot;

public class PersonService {
	
	
	public Person getPerson(String id) {
		return new Person(id, "testPerson");
	}
}
