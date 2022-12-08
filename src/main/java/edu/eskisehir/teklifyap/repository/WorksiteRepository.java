package edu.eskisehir.teklifyap.repository;

import edu.eskisehir.teklifyap.domain.model.User;
import edu.eskisehir.teklifyap.domain.model.Worksite;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface WorksiteRepository extends CrudRepository<Worksite, Long> {

    @Query("SELECT new edu.eskisehir.teklifyap.domain.model.Worksite(w.id,w.name,w.address,w.date,w.locationX,w.locationY,w.userName,w.offer) FROM Worksite w where w.offer.user = ?1")
    List<Worksite> findAllByUser(User user);

    @Modifying
    @Transactional
    @Query("DELETE from WorksiteEmployee we where we.worksite.id = ?1 AND we.employee.id = ?2")
    void deleteEmployeeFromWorksite(Long id, Long employeeId);
}
