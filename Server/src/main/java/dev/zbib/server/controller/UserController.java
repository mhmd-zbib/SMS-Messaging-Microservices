package dev.zbib.server.controller;

import dev.zbib.server.event.RegistrationCompleteEvent;
import dev.zbib.server.model.entity.User;
import dev.zbib.server.model.request.RegisterRequest;
import dev.zbib.server.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    public UserController(UserService userService, ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest, final HttpServletRequest httpRequest) {
        User user = userService.registerUser(registerRequest);
        eventPublisher.publishEvent(new RegistrationCompleteEvent(user, "http://"
                + httpRequest.getServerName()
                + ":" + httpRequest.getLocalPort()
                + httpRequest.getContextPath()));
        return ResponseEntity.ok("Created");
    }

}
