package dev.zbib.server.controller;

import dev.zbib.server.model.entity.User;
import dev.zbib.server.model.request.RegisterRequest;
import dev.zbib.server.event.RegistrationCompleteEvent;
import dev.zbib.server.service.UserService;
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
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        User user = userService.registerUser(request);
        eventPublisher.publishEvent(new RegistrationCompleteEvent(user, "url"));
        return ResponseEntity.ok("Created");
    }

}
