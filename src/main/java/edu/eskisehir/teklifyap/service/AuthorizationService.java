package edu.eskisehir.teklifyap.service;

import edu.eskisehir.teklifyap.config.security.JwtTokenUtility;
import edu.eskisehir.teklifyap.domain.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@AllArgsConstructor
public class AuthorizationService {

    private final UserService userService;
    private final JwtTokenUtility jwtTokenUtility;

    public Long getUserIdFromHttpRequest(HttpServletRequest request) {
        final String token = request.getHeader("Authorization").substring(7);
        return userService.findByEmail(jwtTokenUtility.getUsernameFromToken(token)).getId();
    }

    public User getUserFromHttpRequest(HttpServletRequest request) {
        final String token = request.getHeader("Authorization").substring(7);
        return userService.findByEmail(jwtTokenUtility.getUsernameFromToken(token));
    }

}
