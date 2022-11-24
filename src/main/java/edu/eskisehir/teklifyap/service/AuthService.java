package edu.eskisehir.teklifyap.service;

import edu.eskisehir.teklifyap.config.security.JwtTokenUtility;
import edu.eskisehir.teklifyap.config.security.PasswordEncoder;
import edu.eskisehir.teklifyap.core.Singleton;
import edu.eskisehir.teklifyap.domain.dto.LoginDto;
import edu.eskisehir.teklifyap.domain.dto.RegisterDto;
import edu.eskisehir.teklifyap.domain.model.Token;
import edu.eskisehir.teklifyap.domain.model.User;
import edu.eskisehir.teklifyap.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class AuthService {

    @Value("${base.url}")
    private String baseUrl;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtility jwtTokenUtility;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final MailService mailService;
    private final TokenService tokenService;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtTokenUtility jwtTokenUtility,
                       AuthenticationManager authenticationManager, UserMapper userMapper,
                       MailService mailService, TokenService tokenService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtility = jwtTokenUtility;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
        this.mailService = mailService;
        this.tokenService = tokenService;
    }

    public void register(RegisterDto body) throws MessagingException, UnsupportedEncodingException {

        User user = userMapper.regiterDtoToUser(body);
        user.setRegistrationDate(LocalDateTime.now());
        user.setUpdatedDate(LocalDateTime.now());
        user.setPassword(passwordEncoder.bCryptPasswordEncoder().encode(user.getPassword()));
        userService.save(user);

        sendMailToConfirm(user);

    }

    public void sendMailToConfirm(User user) throws MessagingException, UnsupportedEncodingException {

        String token = Singleton.generateRandomString(20) + System.currentTimeMillis() % 1000000;

        Token tokenObj = new Token();
        tokenObj.setToken(token);
        tokenObj.setEmail(user.getEmail());
        tokenService.save(tokenObj);

        Map<String, String> content = Map.of("name", user.getName() + " " + user.getSurname(),
                "link", baseUrl + "/auth/verify?token=" + token + "&email=" + user.getEmail());
        mailService.sendMail(user.getEmail(), "teklifyap'a hoşgeldin!", "mail-confirmation", content);

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

    public void verify(String token, String email) throws Exception {

        Token tokenObj = tokenService.findByTokenAndEmail(token, email);
        if (tokenObj == null) {
            throw new Exception("TokenNotFound");
        }
        if (!tokenObj.getEmail().equals(email)) {
            throw new Exception("TokenNotMatch");
        }
        User user = userService.findByEmail(email);
        user.setConfirmed(true);
        userService.save(user);
        tokenService.delete(tokenObj);

    }
}
