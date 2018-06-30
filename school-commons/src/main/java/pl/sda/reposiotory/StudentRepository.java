package pl.sda.reposiotory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sda.model.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Integer> {
}
