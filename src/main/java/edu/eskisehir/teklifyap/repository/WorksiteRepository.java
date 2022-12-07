package edu.eskisehir.teklifyap.repository;

import edu.eskisehir.teklifyap.domain.model.User;
import edu.eskisehir.teklifyap.domain.model.Worksite;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorksiteRepository extends CrudRepository<Worksite, Long> {

    @Query("SELECT new edu.eskisehir.teklifyap.domain.model.Worksite(w.id, w.name,w.address) FROM Worksite w where w.offer.user = ?1")
    List<Worksite> findAllByUser(User user);

}
