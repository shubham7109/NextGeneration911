package person;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "login")
public class Login {

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
	
	public Login(String id, String firstName, String lastName, int accesibility, String userName, String password, String location, int status, String ipAddress)
	{
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
	}
	
	public Login() {
	}
	
	public Login(String id)
	{
		super();
		firstName = "";
		lastName = "";
		accesibility = 0;
		userName = "";
		password = "";
		this.id = id;
		location = "";
		status = 0;
		ipAddress = "";
	}


	public void setID(String id)
	{
		this.id = id;
	}
	
	public String getID()
	{
		return id; 
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getLastName()
	{
		return lastName;
	}
	
	public void setAccesibility(int accesibility)
	{
		this.accesibility = accesibility;
	}

	public int getAccesibility()
	{
		return accesibility;
	}
	
	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getPassword()
	{
		return password;
	}
	
	public void setLocation(String location)
	{
		this.location = location;
	}
	public String getLocation()
	{
		return location;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public int getStatus()
	{
		return status;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
}
