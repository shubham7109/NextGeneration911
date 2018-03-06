package address;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "address")
public class Address {
	
	private String address;
	private String city;
	private String state;
	private String zipCode;
	
	
	
	
}
