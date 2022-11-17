package edu.eskisehir.teklifyap.service;

import edu.eskisehir.teklifyap.config.security.JwtTokenUtility;
import edu.eskisehir.teklifyap.config.security.PasswordEncoder;
import edu.eskisehir.teklifyap.domain.dto.LoginDto;
import edu.eskisehir.teklifyap.domain.dto.RegisterDto;
import edu.eskisehir.teklifyap.domain.model.User;
import edu.eskisehir.teklifyap.mapper.UserMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtility jwtTokenUtility;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtTokenUtility jwtTokenUtility,
                       AuthenticationManager authenticationManager, UserMapper userMapper) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtility = jwtTokenUtility;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
    }

    public void register(RegisterDto body) {

        User user = userMapper.regiterDtoToUser(body);
        user.setRegistrationDate(LocalDateTime.now());
        user.setUpdatedDate(LocalDateTime.now());
        user.setPassword(passwordEncoder.bCryptPasswordEncoder().encode(user.getPassword()));
        userService.save(user);

        // send mail to confirm

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
