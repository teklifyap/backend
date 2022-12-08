package edu.eskisehir.teklifyap.repository;

import edu.eskisehir.teklifyap.domain.model.PasswordResetToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordTokenRepository extends CrudRepository<PasswordResetToken,Long> {
    PasswordResetToken findByToken(String token);


}
