package com.example.demo;

public class Logs {

	private String date;
	private String time;
	private String callLength;
	private String operatorName;
	private String phoneNumber; 
	
	public Logs(String date, String time, String callLength, String operatorName, String phoneNumber)
	{
		super();
		this.date = date; 
		this.time = time; 
		this.callLength = callLength; 
		this.operatorName = operatorName; 
		this.phoneNumber = phoneNumber; 
	}
	
	public Logs()
	{
		super();
		date = "";
		time = "";
		callLength = "";
		operatorName = "";
		phoneNumber = "";
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
}
