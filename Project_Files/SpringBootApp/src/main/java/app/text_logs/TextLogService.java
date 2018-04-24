package app.text_logs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TextLogService {
	
	@Autowired
	private TextLogRepository textLogRepository;
	
	public List<TextLog> getAllLogs() {
		List<TextLog> logs = new ArrayList<TextLog>();
		textLogRepository.findAll().forEach(logs::add);
		logs.sort(new TextLogComparator());
		Collections.reverse(logs);
		return logs;
	}
	
	public void addLog(TextLog log) {
		textLogRepository.save(log);
	}

}
