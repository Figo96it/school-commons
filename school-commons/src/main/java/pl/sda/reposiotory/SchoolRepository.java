package pl.sda.reposiotory;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.model.School;

public interface SchoolRepository extends JpaRepository<School, Integer> {
}
