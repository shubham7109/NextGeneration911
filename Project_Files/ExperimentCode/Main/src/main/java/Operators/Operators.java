package Operators;

public class Operators 
{
	private int id;
	private String userName;
	private String password; 
	private String firstName; 
	private String lastName; 
	private int status; 
	
	public Operators(int id, String userName, String password, String firstName, String lastName, int status)
	{
		super();
		this.id = id; 
		this.userName = userName; 
		this.password = password; 
		this.firstName = firstName; 
		this.lastName = lastName;
		this.status = status; 
	}
	
	public Operators(int id)
	{
		super();
		userName = "";
		password = "";
		firstName = "";
		lastName = "";
		this.id = id;
	}
	
	public void setID(int id)
	{
		this.id = id;
	}
	
	public int getID()
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
	
	public void setStatus(int status)
	{
		this.status = status;
	}
	
	public int getStatus() 
	{
		return status;
	}
}
