package edu.eskisehir.teklifyap.repository;

import edu.eskisehir.teklifyap.domain.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {

    Optional<Token> findByTokenAndEmail(String token, String email);

}
