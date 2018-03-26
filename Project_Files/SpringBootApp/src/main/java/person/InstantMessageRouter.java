package person;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

public class InstantMessageRouter {
	
	@Autowired
	private LoginService loginService;
	
	@RequestMapping("/makecall")
	public String makeCall() {
		/* find the first operator whose status = x
		 * return the ip address of that operator */
		Login oper = getAvailableOperator();
		
		/* if the id of the operator is not null,
		 * 	then send it to the windows operator*/
		
		// this will return null if there is no available operator
		return oper.getIpAddress();
	}
	
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
