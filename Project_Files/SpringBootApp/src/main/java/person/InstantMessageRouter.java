package person;

import org.springframework.web.bind.annotation.RequestMapping;

public class InstantMessageRouter {
	
	@RequestMapping("/makeCall")
	public String makeCall() {
		return "";
	}
	
}
