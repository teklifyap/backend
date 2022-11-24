package edu.eskisehir.teklifyap.repository;

import edu.eskisehir.teklifyap.domain.model.Offer;
import edu.eskisehir.teklifyap.domain.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface OfferRepository extends CrudRepository<Offer, Long> {

    @Query("SELECT new edu.eskisehir.teklifyap.domain.model.Offer(o.id, o.status, o.title) FROM Offer o WHERE o.user = ?1")
    List<Offer> findAllByUser(User user);

    @Modifying
    @Transactional
    @Query("DELETE from OfferItem oi where oi.offer.id = ?2 AND oi.item.id = ?1")
    void deleteOfferItem(Long iid, Long oid);

}
