package app.deploy;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeployRepository extends CrudRepository<Deploy, String>{

}
