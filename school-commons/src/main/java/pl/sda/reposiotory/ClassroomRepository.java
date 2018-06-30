package pl.sda.reposiotory;

import org.springframework.data.repository.CrudRepository;
import pl.sda.model.Classroom;

public interface ClassroomRepository extends CrudRepository<Classroom, Integer> {
}
