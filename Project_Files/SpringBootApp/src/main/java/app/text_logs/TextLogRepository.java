package app.text_logs;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextLogRepository extends CrudRepository<TextLog, String> {

}
