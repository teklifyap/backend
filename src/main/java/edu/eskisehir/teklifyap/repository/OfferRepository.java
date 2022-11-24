package edu.eskisehir.teklifyap.repository;

import edu.eskisehir.teklifyap.domain.model.Offer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends CrudRepository<Offer, Long> {


}
