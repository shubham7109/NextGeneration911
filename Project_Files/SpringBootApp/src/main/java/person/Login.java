package person;

public class Login {

	private String id; 
	private String firstName;
	private String lastName;
	private String accesibility; 
	private String userName;
	private String password;
	private String location;
	private int status;
	
	public Login(String id, String firstName, String lastName, String accesibility, String userName, String password, String location, int status)
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
	}
	
	public Login(String id)
	{
		super();
		firstName = "";
		lastName = "";
		accesibility = "";
		userName = "";
		password = "";
		this.id = id;
		location = "";
		status = "";
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
	
	public void setAccesibility(String accesibility)
	{
		this.accesibility = accesibility;
	}

	public String getAccesibility()
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
}
