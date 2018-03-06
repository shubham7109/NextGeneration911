package operator;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorsRepository extends CrudRepository<Operators, String>{

}
