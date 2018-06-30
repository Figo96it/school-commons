package pl.sda.reposiotory;

import org.springframework.data.repository.CrudRepository;
import pl.sda.model.Employee;

public interface GradeRepository extends CrudRepository<Employee, Integer> {
}
