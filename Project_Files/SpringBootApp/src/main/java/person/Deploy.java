package person;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "deploy")
public class Deploy {

	@Id
	private String id; 
	// Ambulance, fire brigade, first responders, state troopers, county officers, swat team
	private String type;
	private String latitude;
	private String longitude; 
	
	public Deploy(String id, String type, String latitude, String longitude)
	{
		super();
		this.id = id;
		this.type = type;
		this.latitude = latitude;
		this.longitude = longitude; 
	}

	public void setID(String id)
	{
		this.id = id;
	}
	
	public String getID()
	{
		return id; 
	}
	
	public void setType(String type)
	{
		this.type = type;
	}

	public String getType()
	{
		return type;
	}

	public void setLatitude(String latitude)
	{
		this.latitude = latitude;
	}

	public String getLatitude()
	{
		return latitude;
	}
	
	public void setLongitude(String longitude)
	{
		this.longitude = longitude;
	}

	public String getLongitude()
	{
		return longitude;
	}
}
