package edu.eskisehir.teklifyap.controller;

import edu.eskisehir.teklifyap.core.SuccessDataMessage;
import edu.eskisehir.teklifyap.core.SuccessMessage;
import edu.eskisehir.teklifyap.domain.dto.UpdateUserDto;
import edu.eskisehir.teklifyap.domain.dto.UserDto;
import edu.eskisehir.teklifyap.domain.model.User;
import edu.eskisehir.teklifyap.service.AuthService;
import edu.eskisehir.teklifyap.service.AuthorizationService;
import edu.eskisehir.teklifyap.service.MailService;
import edu.eskisehir.teklifyap.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/user")
public class UserController {

    private final UserService userService;
    private final AuthorizationService authorizationService;
    private final AuthService authService;

    public UserController(UserService userService, AuthorizationService authorizationService, AuthService authService) {
        this.userService = userService;
        this.authorizationService = authorizationService;
        this.authService = authService;
    }

    @GetMapping("/profile")
    public ResponseEntity<SuccessDataMessage<UserDto>> getProfile(HttpServletRequest request) throws Exception {

        Long id = authorizationService.getUserIdFromHttpRequest(request);
        return ResponseEntity.ok(new SuccessDataMessage<>(userService.getProfile(id), request.getServletPath()));
    }

    @PutMapping("/profile")
    public ResponseEntity<SuccessMessage> updateProfile(HttpServletRequest request, @RequestBody UpdateUserDto body)
            throws Exception {

        Long id = authorizationService.getUserIdFromHttpRequest(request);

        userService.updateProfile(id, body);
        return ResponseEntity.ok(new SuccessMessage("done", request.getServletPath()));
    }

    @DeleteMapping
    public ResponseEntity<SuccessMessage> deleteUser(HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {

        User user = authorizationService.getUserFromHttpRequest(request);
        authService.sendAccountDeleteMail(user);
        return ResponseEntity.ok(new SuccessMessage("Mail gönderildi", request.getServletPath()));
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<SuccessMessage> resetPassword(HttpServletRequest request,
                                                        @RequestParam("email") String userEmail) throws MessagingException, UnsupportedEncodingException {
        User user = userService.findByEmail(userEmail);

        authService.createPasswordResetTokenForUser(user);

        return ResponseEntity.ok(new SuccessMessage("Şifre sıfırlama linki gönderildi", request.getServletPath()));
    }

}
