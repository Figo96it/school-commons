package pl.sda.reposiotory;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.model.Employee;

public interface Employeerepository extends JpaRepository<Employee, Long> {
}
