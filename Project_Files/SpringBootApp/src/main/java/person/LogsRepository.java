package person;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogsRepository extends CrudRepository<Logs, String>{

}
