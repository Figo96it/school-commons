package pl.sda.reposiotory;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.model.StudentGrade;


public interface StudentGgradeRepository extends JpaRepository<StudentGrade, Integer> {
}
