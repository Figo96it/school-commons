package pl.sda.reposiotory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sda.model.Plan;

@Repository
public interface PlanRepository extends CrudRepository<Plan, Integer> {
}
