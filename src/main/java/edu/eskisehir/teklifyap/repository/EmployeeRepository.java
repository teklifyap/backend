package edu.eskisehir.teklifyap.repository;

import edu.eskisehir.teklifyap.domain.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    List<Employee> findAllByUserIdAndDeletedFalse(Long id);
}
