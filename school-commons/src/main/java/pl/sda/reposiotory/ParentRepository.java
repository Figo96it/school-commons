package pl.sda.reposiotory;

import org.springframework.data.repository.CrudRepository;
import pl.sda.model.Parent;

public interface ParentRepository extends CrudRepository<Parent, Integer> {
}
