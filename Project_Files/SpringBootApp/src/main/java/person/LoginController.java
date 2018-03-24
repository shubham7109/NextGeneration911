package person;

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
	private LoginService loginService; 
	
	@RequestMapping("/login")
	public List<Login> getAllLogins()
	{
		return loginService.getAllLogins();
	}
	
	@RequestMapping("/login/{id}")
	public Login getLogin(@PathVariable("id") String id) {
		return loginService.getLogin(id);
	}
	
	@RequestMapping("/login/{userName}/{password}")
	public Login checkPassword(@PathVariable("userName") String userName, @PathVariable("password") String password)
	{
		return loginService.checkPassword(userName, password);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/login")
	public void addLogin(@RequestBody Login log) 
	{
		loginService.addLogin(log);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/login/{id}")
	public void updateLogin(@RequestBody Login login, @PathVariable("id") String id) 
	{
		loginService.updateLogin(id, login);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/login/{id}")
	public void deleteLogin(@PathVariable("id") String id)
	{
		loginService.deleteLogin(id);
	}
}

