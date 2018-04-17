package app.logs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogsService {

	@Autowired
	private LogsRepository logsRepository;

	/**
	 * @return list of logs
	 */
	public List<Logs> getAllLogs() {

		List<Logs> logs = new ArrayList<>();
		logsRepository.findAll().forEach(logs::add);
		return logs;
	}

	/**
	 * adds a log to the database
	 * @param log Logs
	 */
	public void addLogs(Logs log) {
		logsRepository.save(log);
	}
}

