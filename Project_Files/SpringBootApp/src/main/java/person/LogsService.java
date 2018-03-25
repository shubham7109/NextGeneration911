package person;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogsService {

	@Autowired
	private LogsRepository logsRepository;
	
	public List<Logs> getAllOperators() {

		List<Login> logs = new ArrayList<>();
		logsRepository.findAll().forEach(logs::add);
		return logs;
	}

	public void addOperators(Logs log) {
		loginRepository.save(log);
	}
}

