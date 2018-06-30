package pl.sda.reposiotory;

import org.springframework.data.repository.CrudRepository;
import pl.sda.model.School;

public interface SchoolRepository extends CrudRepository<School, Integer> {
}
