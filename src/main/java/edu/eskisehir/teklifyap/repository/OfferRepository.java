package edu.eskisehir.teklifyap.repository;

import edu.eskisehir.teklifyap.domain.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {


}
