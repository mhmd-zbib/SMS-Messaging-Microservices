package dev.zbib.server.controller;

import dev.zbib.server.model.request.LoginRequest;
import dev.zbib.server.model.request.RegisterRequest;
import dev.zbib.server.service.AuthService;
import dev.zbib.server.service.UserService;
import dev.zbib.server.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;
    private final VerificationTokenService verificationTokenService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
        return ResponseEntity.ok("Created");
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
//        authService.login(loginRequest);
//        return ResponseEntity.ok("Login");
//    }

//    @GetMapping("/verify")
//    public ResponseEntity<String> verifyToken(@RequestParam("token") String token) {
//        userService.verifyUser(token);
//        return ResponseEntity.ok("Verified");
//    }


    @GetMapping("/resendToken")
    public ResponseEntity<String> resendVerifyToken(@RequestParam("token") String oldToken) {
        verificationTokenService.regenerateToken(oldToken);
        return ResponseEntity.ok("Verification token sent ");
    }
}
