package edu.eskisehir.teklifyap.service;

import edu.eskisehir.teklifyap.config.security.JwtTokenUtility;
import edu.eskisehir.teklifyap.domain.model.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AuthorizationService {

    private final UserService userService;
    private final JwtTokenUtility jwtTokenUtility;

    public AuthorizationService(UserService userService, JwtTokenUtility jwtTokenUtility) {
        this.userService = userService;
        this.jwtTokenUtility = jwtTokenUtility;
    }

    public Long getUserIdFromHttpRequest(HttpServletRequest request) {
        return getUserFromHttpRequest(request).getId();
    }

    public User getUserFromHttpRequest(HttpServletRequest request) {
        final String token = request.getHeader("Authorization").substring(7);
        return userService.findByEmail(jwtTokenUtility.getUsernameFromToken(token));
    }

}
