package pl.sda.reposiotory;

import org.springframework.data.repository.CrudRepository;
import pl.sda.model.StudentGrade;


public interface StudentGgradeRepository extends CrudRepository<StudentGrade, Integer> {
}
