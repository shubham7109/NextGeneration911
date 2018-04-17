package app.login;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
	
	@Autowired
	private LoginRepository loginRepository;

	/**
	 * @return list of logins
	 */
	public List<Login> getAllLogins() 
	{
		List<Login> logins = new ArrayList<>();
		loginRepository.findAll().forEach(logins::add);
		return logins;

	}

	/**
	 * @param id of login
	 * @return login
	 */
	public Login getLogin(String id) 
	{
		return loginRepository.findOne(id);
	}

	/**
	 * adds a login to the database
	 * @param log Login
	 */
	public void addLogin(Login log) {
		loginRepository.save(log);
	}

	/**
	 * updates a login in the database
	 * @param id of login
	 * @param log the Login
	 */
	public void updateLogin(String id, Login log) 
	{
		loginRepository.save(log);
	}

	/**
	 * deletes a login
	 * @param id of login
	 */
	public void deleteLogin(String id) 
	{
		loginRepository.delete(id);
	}

	/**
	 * checks password of user based upon userName
	 * @param userName of login
	 * @param password of login
	 * @return true is password is correct, otherwise false
	 */
	public boolean checkPassword(String userName, String password)
	{
		List<Login> login;
		login = getAllLogins();
		for(int i = 0; i < login.size(); i++)
			if(login.get(i).getUserName().equals(userName) && login.get(i).getPassword().equals(password))
				return true;
		return false;
	}
}
