package dev.zbib.server.controller;

import dev.zbib.server.model.request.RegisterRequest;
import dev.zbib.server.repository.VerificationTokenRepository;
import dev.zbib.server.service.UserService;
import dev.zbib.server.service.VerificationTokenService;
import lombok.extern.log4j.Log4j2;
import org.springdoc.webmvc.core.service.RequestService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Log4j2
public class UserController {

    private final UserService userService;
    private final VerificationTokenService verificationTokenService;

    public UserController(UserService userService, ApplicationEventPublisher eventPublisher, RequestService requestBuilder, VerificationTokenService verificationTokenService, VerificationTokenRepository verificationTokenRepository) {
        this.userService = userService;
        this.verificationTokenService = verificationTokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        userService.registerUser(registerRequest);
        return ResponseEntity.ok("Created");
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyToken(@RequestParam("token") String token) {
        userService.verifyUser(token);
        return ResponseEntity.ok("Verified");
    }

    @GetMapping("/resendToken")
    public ResponseEntity<String> resendVerifyToken(@RequestParam("token") String oldToken) {
        verificationTokenService.regenerateToken(oldToken);
        return ResponseEntity.ok("Verification token sent ");
    }
}
