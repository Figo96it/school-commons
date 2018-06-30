package pl.sda.reposiotory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sda.model.Parent;

@Repository
public interface ParentRepository extends CrudRepository<Parent, Integer> {
}
