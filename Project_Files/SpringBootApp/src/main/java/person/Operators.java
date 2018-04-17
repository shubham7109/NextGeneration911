package person;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "operators")
public class Operators
{
	@Id
	private String id;
	private String firstName;
	private String lastName;
	private int accesibility;
	private String userName;
	private String password;
	private String location;
	private int status;
	private String ipAddress;
	private String image;
	
	public Operators() {
		super();
	}

	public Operators(String id, String firstName, String lastName, int accesibility, String userName, String password,
			String location, int status, String ipAddress, String image) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.accesibility = accesibility;
		this.userName = userName;
		this.password = password;
		this.location = location;
		this.status = status;
		this.ipAddress = ipAddress;
		this.image = image;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAccesibility() {
		return accesibility;
	}

	public void setAccesibility(int accesibility) {
		this.accesibility = accesibility;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
	
}