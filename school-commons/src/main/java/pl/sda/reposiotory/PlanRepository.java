package pl.sda.reposiotory;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.model.Plan;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}
