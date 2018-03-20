package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	@Autowired
	private LoginService LoginService; 
	
	@RequestMapping("/login")
	public List<Login> getAllLogins()
	{
		return LoginService.getAllLogins();
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/login")
	public void addLogin(@RequestBody Login log) 
	{
		LoginService.addLogin(log);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/login/{id}")
	public void updateLogin(@RequestBody Login login, @PathVariable("id") String id) 
	{
		LoginService.updateLogin(id, login);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/login/{id}")
	public void deleteLogin(@PathVariable("id") String id)
	{
		LoginService.deleteLogin(id);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/login/{username}/{password}")
	public void login(@RequestBody String username, String password)
	{
		LoginService.checkPassword(username, password);
	}

}

