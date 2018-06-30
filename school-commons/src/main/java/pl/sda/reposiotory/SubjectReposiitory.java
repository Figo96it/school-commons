package pl.sda.reposiotory;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.model.Subject;

public interface SubjectReposiitory extends JpaRepository<Subject, Integer> {
}
