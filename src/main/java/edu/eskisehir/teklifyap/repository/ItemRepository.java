package edu.eskisehir.teklifyap.repository;

import edu.eskisehir.teklifyap.domain.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {


}
