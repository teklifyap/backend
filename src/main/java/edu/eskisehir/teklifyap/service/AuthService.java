package edu.eskisehir.teklifyap.service;

import edu.eskisehir.teklifyap.config.security.JwtTokenUtility;
import edu.eskisehir.teklifyap.config.security.PasswordEncoder;
import edu.eskisehir.teklifyap.domain.dto.LoginDto;
import edu.eskisehir.teklifyap.domain.dto.RegisterDto;
import edu.eskisehir.teklifyap.domain.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtility jwtTokenUtility;
    private final AuthenticationManager authenticationManager;

    public void register(RegisterDto body) {

        User user = User.builder()
                .name(body.getName())
                .surname(body.getSurname())
                .email(body.getEmail())
                .confirmed(false)
                .password(passwordEncoder.bCryptPasswordEncoder().encode(body.getPassword()))
                .registrationDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        userService.save(user);

        // send mail tyo confirm

    }

    public String login(LoginDto body) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword()));
        } catch (DisabledException e) {
            throw new Exception("DisabledException");
        } catch (BadCredentialsException e) {
            throw new Exception("BadCredentialsException");
        }
        return jwtTokenUtility.generateToken(userService.findByEmail(body.getEmail()), false);
    }
}
