package app.logs;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "logs")
public class Logs {

	@Id
	private String id;
	private String date;
	private String time;
	private String callLength;
	private String operatorName;
	private String phoneNumber; 
	
	public Logs(String id, String date, String time, String callLength, String operatorName, String phoneNumber)
	{
		super();
		this.id = id;
		this.date = date; 
		this.time = time; 
		this.callLength = callLength; 
		this.operatorName = operatorName; 
		this.phoneNumber = phoneNumber; 
	}

	public Logs(){
	}

	public void setID(String id)
	{
		this.id = id;
	}

	public String getID()
	{
		return id;
	}

	public void setDate(String date)
	{
		this.date = date;
	}
	
	public String getDate() 
	{
		return date;
	}
	
	public void setTime(String time)
	{
		this.time = time;
	}
	
	public String getTime()
	{
		return time;
	}
	
	public void setCallLength(String callLength)
	{
		this.callLength = callLength;
	}
	
	public String getCallLength()
	{
		return callLength;
	}
	
	public void setOperatorName(String operatorName)
	{
		this.operatorName = operatorName;
	}
	
	public String getOperatorName()
	{
		return operatorName;
	}
	
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
	
	public String getPhoneNumber() 
	{
		return phoneNumber;
	}
	
	public String toString() {
		return "Log: " + operatorName;
	}
}
