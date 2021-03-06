package app.logs;

import java.util.ArrayList;
import java.util.Collections;
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
		logs.sort(new LogsComparator());
		Collections.reverse(logs);
		return logs;
	}

	/**
	 * adds a log to the database
	 * @param log Logs
	 */
	public void addLogs(Logs log) {
		if (log.getOperatorName() != "Sumon Biswas") {
			logsRepository.save(log);
		}
	}
}

