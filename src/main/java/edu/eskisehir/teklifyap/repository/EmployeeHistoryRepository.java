package edu.eskisehir.teklifyap.repository;

import edu.eskisehir.teklifyap.domain.model.EmployeeHistory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeHistoryRepository extends CrudRepository<EmployeeHistory, Long> {

    List<EmployeeHistory> findAllByEid(Long id);

}

