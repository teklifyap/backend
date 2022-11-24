package edu.eskisehir.teklifyap.controller;

import edu.eskisehir.teklifyap.core.SuccessDataMessage;
import edu.eskisehir.teklifyap.core.SuccessMessage;
import edu.eskisehir.teklifyap.domain.dto.UpdateUserDto;
import edu.eskisehir.teklifyap.domain.dto.UserDto;
import edu.eskisehir.teklifyap.service.AuthorizationService;
import edu.eskisehir.teklifyap.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v2/user")
public class UserController {

    private final UserService userService;
    private final AuthorizationService authorizationService;

    public UserController(UserService userService, AuthorizationService authorizationService) {
        this.userService = userService;
        this.authorizationService = authorizationService;
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

}
