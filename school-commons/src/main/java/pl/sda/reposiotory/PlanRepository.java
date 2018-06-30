package pl.sda.reposiotory;

import org.springframework.data.repository.CrudRepository;
import pl.sda.model.Plan;

public interface PlanRepository extends CrudRepository<Plan, Long> {
}
