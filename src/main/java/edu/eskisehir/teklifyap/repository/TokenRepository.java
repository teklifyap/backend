package edu.eskisehir.teklifyap.repository;

import edu.eskisehir.teklifyap.domain.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Token findByTokenAndEmail(String token, String email);

}
