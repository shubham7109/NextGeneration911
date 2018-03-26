package person;

import org.springframework.web.bind.annotation.RequestMapping;

public class InstantMessageRouter {

	@RequestMapping("/makecall")
	public String makeCall() {
		return "";
	}

}
