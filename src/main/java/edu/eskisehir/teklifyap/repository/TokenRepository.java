package edu.eskisehir.teklifyap.repository;

import edu.eskisehir.teklifyap.domain.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByTokenAndEmail(String token, String email);

}
