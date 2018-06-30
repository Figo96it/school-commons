package pl.sda.reposiotory;

import org.springframework.data.repository.CrudRepository;
import pl.sda.model.Subject;

public interface SubjectReposiitory extends CrudRepository<Subject, Integer> {
}
