package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
	
	@Autowired
	private LoginRepository loginRepository;
	
	public List<Login> getAllLogins() 
	{
		List<Login> logins = new ArrayList<>();
		loginRepository.findAll().forEach(logins::add);
		return logins;

	}
	
	public Login getLogin(String id) 
	{
		return loginRepository.findOne(id);
	}
	
	public void addLogin(Login log) {
		loginRepository.save(log);
	}
	
	public void updateLogin(String id, Login log) 
	{
		loginRepository.save(log);
	}
	
	public void deleteLogin(String id) 
	{
		loginRepository.delete(id);
	}
	
	public Login checkPassword(String userName, String password)
	{
		if(loginRepository.findOne(userName).getPassword().equals(password)) 
			return loginRepository.findOne(userName);
		return null;
	}
}
