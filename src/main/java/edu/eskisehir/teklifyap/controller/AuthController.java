package edu.eskisehir.teklifyap.controller;

import edu.eskisehir.teklifyap.core.SuccessDataMessage;
import edu.eskisehir.teklifyap.core.SuccessMessage;
import edu.eskisehir.teklifyap.domain.dto.LoginDto;
import edu.eskisehir.teklifyap.domain.dto.RegisterDto;
import edu.eskisehir.teklifyap.service.AuthService;
import edu.eskisehir.teklifyap.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(HttpServletRequest request, @RequestBody RegisterDto body) {
        authService.register(body);
        return ResponseEntity.ok(new SuccessMessage("Kayıt başarılı", request.getServletPath()));
    }

    @PostMapping
    public ResponseEntity<?> login(HttpServletRequest request, @RequestBody LoginDto body) throws Exception {

        return ResponseEntity.ok(new SuccessDataMessage<>(authService.login(body), request.getServletPath()));
    }
}
