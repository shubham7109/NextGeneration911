package person;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LogsController {

	@Autowired
	private LogsService LogsService; 
	
	@RequestMapping("/logs")
	public List<Logs> getAllOperators()
	{
		return LogsService.getAllOperators();
	}
	
	
//	@RequestMapping(method=RequestMethod.POST, value="/logs")
//	public void addOperators(@RequestBody Logs Logs) 
//	{
//		LogsService.addOperator(Logs);
//	}
}
