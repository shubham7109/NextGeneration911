package app.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	@Autowired
	private LoginService loginService;

	/**
	 * @return list of all logins
	 */
	@RequestMapping("/login")
	public Iterable<Login> getAllLogins()
	{
		return loginService.getAllLogins();
	}

	/**
	 * @param id
	 * @return login with the given id or null if the id doesn't exist
	 */
	
	@RequestMapping("/login/{id}")
	public Login getLogin(@PathVariable("id") String id) {
		return loginService.getLogin(id);
	}
	

	/**
	 * Checks if the password entered for the given username is correct
	 * @param userName username of operator
	 * @param password password of operator
	 */
	/*
	@RequestMapping("/login/{userName}/{password}")
	public boolean checkPassword(@PathVariable("userName") String userName, @PathVariable("password") String password)
	{
		return loginService.checkPassword(userName, password);
	}
	*/
	
	/**
	 * adds a login to the database
	 * @param log Login object
	 */
	@RequestMapping(method=RequestMethod.POST, value="/login")
	public void addLogin(@RequestBody Login log) 
	{
		loginService.addLogin(log);
	}

	/**
	 * Updates a login in the database
	 * @param login Login object
	 * @param id of login
	 */
	@RequestMapping(method=RequestMethod.PUT, value="/login/{id}")
	public void updateLogin(@RequestBody Login login, @PathVariable("id") String id) 
	{
		loginService.updateLogin(id, login);
	}

	/**
	 * Deletes a login in the database
	 * @param id of login
	 */
	@RequestMapping(method=RequestMethod.DELETE, value="/login/{id}")
	public void deleteLogin(@PathVariable("id") String id)
	{
		loginService.deleteLogin(id);
	}
}

