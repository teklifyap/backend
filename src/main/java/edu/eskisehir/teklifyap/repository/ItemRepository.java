package edu.eskisehir.teklifyap.repository;

import edu.eskisehir.teklifyap.domain.model.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

    List<Item> findAllByUserIdAndDeletedFalse(Long id);

}
