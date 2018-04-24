package app.text_logs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TextLogController {
	
	@Autowired
	private TextLogService textLogService;
	
	@RequestMapping("/textlogs")
	public List<TextLog> getAllLogs() {
		return textLogService.getAllLogs();
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/textlogs")
	public void addTextLog(@RequestBody TextLog logs) {
		textLogService.addLog(logs);
	}
}
