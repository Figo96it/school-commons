package pl.sda.reposiotory;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
