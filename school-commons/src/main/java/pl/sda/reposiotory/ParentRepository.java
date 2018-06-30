package pl.sda.reposiotory;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.model.Parent;

public interface ParentRepository extends JpaRepository<Parent, Integer> {
}
