package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class LoginService {
	

	private List<Login> login = new ArrayList<>(Arrays.asList(
			new Login("123", "Shubham", "Sharma", "1", "shubham", "abc", "Ames", 1),
			new Login("456", "Paul", "Biermann", "2", "shubham", "def", "Ames", 2),
			new Login("789", "Michael", "O", "3", "shubham", "ghi", "Ames", 3)
			));
	
	public List<Login> getAllLogins() 
	{
		return login;
	}
	
	public Login getLogin(String id) 
	{
		for(int i = 0; i < login.size(); i++)
		{
			if(login.get(i).getID() == id)
				return login.get(i); 
		}
		return null;
	}
	
	public void addLogin(Login log) {
		login.add(log);
	}
	
	public void updateLogin(String id, Login log) 
	{
		for (int i = 0; i < login.size(); i++) 
		{
			Login o = login.get(i);
			if (o.getID() == id) 
			{
				login.set(i, log);
				return;
			}
		}
	}
	
	public void deleteLogin(String id) 
	{
		for (int i = 0; i < login.size(); i++) 
		{
			Login o = login.get(i);
			if (o.getID() == id) 
			{
				login.remove(i);
			}
		}
	}
	
	public Login checkPassword(String username, String password)
	{
		for(int i = 0; i < login.size(); i++)
		{
			if(username.equals(login.get(i).getUserName()))
			{
				if(password.equals(login.get(i).getPassword()))
				{
					return login.get(i);
				}
			}
		}
		return null;
	}
}
