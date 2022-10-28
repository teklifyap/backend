package edu.eskisehir.teklifyap.controller;

import edu.eskisehir.teklifyap.core.SuccessDataMessage;
import edu.eskisehir.teklifyap.domain.dto.UserDto;
import edu.eskisehir.teklifyap.service.AuthorizationService;
import edu.eskisehir.teklifyap.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v2/users")
public class UserController {

    private final UserService userService;
    private final AuthorizationService authorizationService;

    public UserController(UserService userService, AuthorizationService authorizationService) {
        this.userService = userService;
        this.authorizationService = authorizationService;
    }

    @GetMapping("/profile")
    public ResponseEntity<SuccessDataMessage<UserDto>> getProfile(HttpServletRequest request,
                                                                  @RequestParam(value = "user", required = false) String uid)
            throws Exception {

        Long id = authorizationService.getUserFromHttpRequest(request);
        return ResponseEntity.ok(new SuccessDataMessage<>(userService.getProfile(id), request.getServletPath()));
    }

}
