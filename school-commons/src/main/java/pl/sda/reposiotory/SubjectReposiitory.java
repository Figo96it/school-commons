package pl.sda.reposiotory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sda.model.Subject;

@Repository
public interface SubjectReposiitory extends CrudRepository<Subject, Integer> {
}
