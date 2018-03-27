package person;

import java.util.ArrayList;
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
	private LogsService logsService;
	
	@RequestMapping("/logs")
	public List<Logs> getAllLogs()
	{
		List<Logs> s = logsService.getAllLogs();
		
		s.sort(new LogsComparator());
		
		return s;
	}
	

	@RequestMapping(method=RequestMethod.POST, value="/logs")
	public void addLogs(@RequestBody Logs logs)
	{
		logsService.addLogs(logs);
	}
	
}
