package edu.eskisehir.teklifyap.controller;

import edu.eskisehir.teklifyap.core.SuccessDataMessage;
import edu.eskisehir.teklifyap.core.SuccessMessage;
import edu.eskisehir.teklifyap.domain.dto.LoginDto;
import edu.eskisehir.teklifyap.domain.dto.PasswordDto;
import edu.eskisehir.teklifyap.domain.dto.RegisterDto;
import edu.eskisehir.teklifyap.domain.model.User;
import edu.eskisehir.teklifyap.service.AuthService;
import edu.eskisehir.teklifyap.service.MailService;
import edu.eskisehir.teklifyap.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    private final MailSender mailSender;

    private final MailService mailService;

    @PostMapping("/register")
    public ResponseEntity<SuccessMessage> register(HttpServletRequest request, @RequestBody RegisterDto body)
            throws MessagingException, UnsupportedEncodingException {
        authService.register(body);
        return ResponseEntity.ok(new SuccessMessage("Kayıt başarılı", request.getServletPath()));
    }

    @PostMapping
    public ResponseEntity<SuccessDataMessage<String>> login(HttpServletRequest request, @RequestBody LoginDto body) throws Exception {
        return ResponseEntity.ok(new SuccessDataMessage<>(authService.login(body), request.getServletPath()));
    }

    @GetMapping("/verify")
    public ResponseEntity<SuccessMessage> verify(HttpServletRequest request, @RequestParam String token,
                                                 @RequestParam("email") String email) throws Exception {
        authService.verify(token, email);
        return ResponseEntity.ok(new SuccessMessage("Hesabınız onaylandı", request.getServletPath()));
    }

    @GetMapping("/delete-me")
    public ResponseEntity<SuccessMessage> deleteAccount(HttpServletRequest request,
                                                        @RequestParam String token,
                                                        @RequestParam("email") String email) throws Exception {

        userService.deleteUser(email, token);
        return ResponseEntity.ok(new SuccessMessage("Hesabınız silindi", request.getServletPath()));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<SuccessMessage> resetPassword(HttpServletRequest request,
                                                        @RequestParam String email,
                                                        @RequestParam String token)
            throws MessagingException, UnsupportedEncodingException {
        userService.resetPassword(email, token);
        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }
}
