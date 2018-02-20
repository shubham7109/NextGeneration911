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
	private String userName; 
	private String password; 
	private String firstName; 
	private String lastName;
	private String location;
	private String status; 
	
	public Operators(String id, String userName, String password, String firstName, String lastName,  String location, String status)
	{
		super();
		this.id = id; 
		this.userName = userName; 
		this.password = password; 
		this.firstName = firstName; 
		this.lastName = lastName;
		this.location = location;
		this.status = status; 
	}
	
	public Operators(String id)
	{
		super();
		userName = "";
		password = "";
		firstName = "";
		lastName = "";
		location = "";
		this.id = id;
	}
	
	public Operators() {
		super();
	}
	
	public void setID(String id)
	{
		this.id = id;
	}
	
	public String getID()
	{
		return id;
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

	public void setLocation(String location){ this.location = location; }

	public String getLocation(){ return location; }
	
	public void setStatus(String status)
	{
		this.status = status;
	}
	
	public String getStatus() 
	{
		return status;
	}
}
