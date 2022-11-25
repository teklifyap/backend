package edu.eskisehir.teklifyap.repository;

import edu.eskisehir.teklifyap.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findById(long id);

    Optional<User> findByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "delete from users where id = ?1", nativeQuery = true)
    void deleteUser(long id);

}
