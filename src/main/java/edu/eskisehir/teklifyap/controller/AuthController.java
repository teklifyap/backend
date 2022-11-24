package edu.eskisehir.teklifyap.controller;

import edu.eskisehir.teklifyap.core.SuccessDataMessage;
import edu.eskisehir.teklifyap.core.SuccessMessage;
import edu.eskisehir.teklifyap.domain.dto.LoginDto;
import edu.eskisehir.teklifyap.domain.dto.RegisterDto;
import edu.eskisehir.teklifyap.service.AuthService;
import edu.eskisehir.teklifyap.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(HttpServletRequest request, @RequestBody RegisterDto body) throws MessagingException, UnsupportedEncodingException {
        authService.register(body);
        return ResponseEntity.ok(new SuccessMessage("Kayıt başarılı", request.getServletPath()));
    }

    @PostMapping
    public ResponseEntity<?> login(HttpServletRequest request, @RequestBody LoginDto body) throws Exception {
        return ResponseEntity.ok(new SuccessDataMessage<>(authService.login(body), request.getServletPath()));
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verify(HttpServletRequest request, @RequestParam String token, @RequestParam("email") String email)
            throws Exception {
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
}
