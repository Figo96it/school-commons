package pl.sda.reposiotory;

import org.springframework.data.repository.CrudRepository;
import pl.sda.model.Student;

public interface StudentRepository extends CrudRepository<Student, Integer> {
}
