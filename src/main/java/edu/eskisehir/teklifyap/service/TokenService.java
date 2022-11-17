package edu.eskisehir.teklifyap.service;

import edu.eskisehir.teklifyap.domain.model.Token;
import edu.eskisehir.teklifyap.repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public Token findByTokenAndEmail(String token, String email) {
        return tokenRepository.findByTokenAndEmail(token, email);
    }

    public void save(Token tokenObj) {
        tokenRepository.save(tokenObj);
    }

    public void delete(Token tokenObj) {
        tokenRepository.delete(tokenObj);
    }
}
