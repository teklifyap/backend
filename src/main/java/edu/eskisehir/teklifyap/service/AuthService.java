package edu.eskisehir.teklifyap.service;

import edu.eskisehir.teklifyap.config.security.JwtTokenUtility;
import edu.eskisehir.teklifyap.config.security.PasswordEncoder;
import edu.eskisehir.teklifyap.core.Singleton;
import edu.eskisehir.teklifyap.domain.dto.LoginDto;
import edu.eskisehir.teklifyap.domain.dto.RegisterDto;
import edu.eskisehir.teklifyap.domain.model.Item;
import edu.eskisehir.teklifyap.domain.model.PasswordResetToken;
import edu.eskisehir.teklifyap.domain.model.Token;
import edu.eskisehir.teklifyap.domain.model.User;
import edu.eskisehir.teklifyap.mapper.ItemMapper;
import edu.eskisehir.teklifyap.mapper.UserMapper;
import edu.eskisehir.teklifyap.repository.PasswordTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AuthService {

    @Autowired
    private ItemMapper itemMapper;
    @Value("${base.url}")
    private String baseUrl;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtility jwtTokenUtility;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final MailService mailService;
    private final TokenService tokenService;
    private final ItemService itemService;
    private final PasswordTokenRepository passwordTokenRepository;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtTokenUtility jwtTokenUtility,
                       AuthenticationManager authenticationManager, UserMapper userMapper,
                       MailService mailService, TokenService tokenService, ItemService itemService, PasswordTokenRepository passwordTokenRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtility = jwtTokenUtility;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
        this.mailService = mailService;
        this.tokenService = tokenService;
        this.itemService = itemService;
        this.passwordTokenRepository = passwordTokenRepository;
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

        User user = userService.findByEmail(email);
        user.setConfirmed(true);

        List<String> allLines = List.of("Beton (C25)-M3", "Beton (C30)-M3", "Kalıp İşçilik-M2", "Demir İşçilik-TON",
                "İnşaat Demiri-TON", "Hasır Çelik-TON", "Hafriyat Kazı-M3", "Hafriyat Dolgu-M3", "Tuğla Duvar-M2",
                "Kaba Sıva-M2", "Seramik Fayans Kaplama-M2", "Alçı Sıva-M2", "Beton İşçilik-M2");
        List<Item> items = new LinkedList<>();
        for (String line : allLines) {
            String[] split = line.split("-");
            Item item = new Item();
            item.setName(split[0]);
            item.setUnit(itemMapper.toUnit(split[1]));
            item.setValue(0);
            item.setUser(user);
            items.add(item);
        }

        itemService.saveAll(items);
        userService.save(user);
        tokenService.delete(tokenObj);
    }

    public void sendAccountDeleteMail(User user) throws MessagingException, UnsupportedEncodingException {

        String token = Singleton.generateRandomString(20) + System.currentTimeMillis() % 1000000;

        Token tokenObj = new Token();
        tokenObj.setToken(token);
        tokenObj.setEmail(user.getEmail());
        tokenService.save(tokenObj);

        String link = baseUrl + "/auth/delete-me?token=" + token + "&email=" + user.getEmail();
//        System.out.println(link.replace("https://teklifyap-api.oguzhanercelik.dev", "http://localhost:8080"));
        Map<String, String> content = Map.of("name", user.getName(), "link", link);
        mailService.sendMail(user.getEmail(), "Hesap silme", "delete-account", content);
    }

    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }

    public void createPasswordResetTokenForUser(User user) throws MessagingException, UnsupportedEncodingException {
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(UUID.randomUUID().toString());
        resetToken.setEmail(user.getEmail());

        passwordTokenRepository.save(resetToken);
        mailService.sendMail(user.getEmail(), "Şifre sıfırlama", "reset-password", Map.of("link", baseUrl + "/auth/reset-password?token=" + resetToken.getToken()));
    }


    /**
     *
     *
     * @PostMapping("/resetPassword")
     *     public ResponseEntity<SuccessMessage> resetPassword(HttpServletRequest request,
     *                                                         @RequestParam("email") String userEmail) {
     *         User user = userService.findByEmail(userEmail);
     *
     *         String token = UUID.randomUUID().toString();
     *         userService.createPasswordResetTokenForUser(user, token);
     *         mailSender.send(constructResetTokenEmail(getAppUrl(request),
     *                 request.getLocale(), token, user));
     *         return ResponseEntity.ok(new SuccessMessage("Şifre sıfırlama linki gönderildi", request.getServletPath()));
     *     }
     *
     *     @PostMapping("/savePassword")
     *     public ResponseEntity<SuccessMessage> savePassword(final Locale locale, PasswordDto passwordDto) {
     *
     *         String result = authService.validatePasswordResetToken(passwordDto.getToken());
     *
     *         if (result != null) {
     *             return ResponseEntity.ok(new SuccessMessage(result, "/auth/savePassword"));
     *         }
     *
     *         Optional user = userService.getUserByPasswordResetToken(passwordDto.getToken());
     *         if (user.isPresent()) {
     *             userService.changeUserPassword(user.get(), passwordDto.getNewPassword());
     *             return new GenericResponse(messages.getMessage(
     *                     "message.resetPasswordSuc", null, locale));
     *         } else {
     *             return new GenericResponse(messages.getMessage(
     *                     "auth.message.invalid", null, locale));
     *         }
     *     }
     *
     *     private SimpleMailMessage constructResetTokenEmail(String contextPath, Locale locale, String token, User user) {
     *         String url = contextPath + "/user/changePassword?token=" + token;
     *         String message = messages.getMessage("message.resetPassword",
     *                 null, locale);
     *         return constructEmail("Reset Password", message + " \r\n" + url, user);
     *
     *     }
     *
     *
     *     private SimpleMailMessage constructEmail(String subject, String body,
     *                                              User user) {
     *         mailService.sendMail(user.getEmail(), subject, body, );
     *         SimpleMailMessage email = new SimpleMailMessage();
     *         email.setSubject(subject);
     *         email.setText(body);
     *         email.setTo(user.getEmail());
     *         email.setFrom(env.getProperty("support.email"));
     *         return email;
     *     }
     */
}
