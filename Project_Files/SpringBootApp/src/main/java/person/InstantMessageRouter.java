package person;

import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InstantMessageRouter {
	
	private Client client;
	
	@Autowired
	private LoginService loginService;
	
	
	/**
	 * @return an available operator
	 */
	@RequestMapping("/makecall")
	public String makeCall() {
		Login oper = getAvailableOperator();
		return oper.getIpAddress();
	}
	
	/**
	 * @param id of the person who is calling
	 * @return an available operator
	 */
	@RequestMapping("/makecall/{id}")
	public String makeIdCall(@PathVariable("id") String id) {
		Login oper = getAvailableOperator();
		return oper.getIpAddress();
	}
	
	/* return the first available operator whose status is 0 (who is available) */
	private Login getAvailableOperator() {
		List<Login> operators = loginService.getAllLogins();
		for (int i = 0; i < operators.size(); i++) {
			if (operators.get(i).getStatus() == 0) {
				return operators.get(i);
			}
		}
		return new Login();
	}

}
