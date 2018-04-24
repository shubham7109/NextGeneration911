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
	private String messages;
	private String operatorId;

	public Logs(String id, String date, String time, String callLength, String operatorName, String phoneNumber,
			String messages, String operatorId) {
		super();
		this.id = id;
		this.date = date;
		this.time = time;
		this.callLength = callLength;
		this.operatorName = operatorName;
		this.phoneNumber = phoneNumber;
		this.messages = messages;
		this.operatorId = operatorId;
	}

	public Logs(){
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
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String toString() {
		return "Log: " + operatorName + " " + date + " " + time;
	}
}
