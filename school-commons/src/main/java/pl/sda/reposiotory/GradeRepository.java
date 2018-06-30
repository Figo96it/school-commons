package pl.sda.reposiotory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sda.model.Employee;

@Repository
public interface GradeRepository extends CrudRepository<Employee, Integer> {
}
