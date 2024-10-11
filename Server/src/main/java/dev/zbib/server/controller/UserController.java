package dev.zbib.server.controller;

import dev.zbib.server.event.RegistrationCompleteEvent;
import dev.zbib.server.model.entity.User;
import dev.zbib.server.model.entity.VerificationToken;
import dev.zbib.server.model.request.RegisterRequest;
import dev.zbib.server.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
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
    private final ApplicationEventPublisher eventPublisher;
    private final RequestService requestBuilder;

    public UserController(UserService userService, ApplicationEventPublisher eventPublisher, RequestService requestBuilder) {
        this.userService = userService;
        this.eventPublisher = eventPublisher;
        this.requestBuilder = requestBuilder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest, final HttpServletRequest httpRequest) {
        User user = userService.registerUser(registerRequest);
        eventPublisher.publishEvent(new RegistrationCompleteEvent(user,
                applicationUrl(httpRequest)));
        return ResponseEntity.ok("Created");
    }

    @GetMapping("/token/resend")
    public ResponseEntity<String> resendVerifyToken(@RequestParam("token") String oldToken,
                                                    final HttpServletRequest httpRequest) {
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);
        User user = verificationToken.getUser();
        resendVerifyTokenMail(user, applicationUrl(httpRequest), verificationToken);
        return ResponseEntity.ok("Verification token sent");
    }


    @GetMapping("/token/verify")
    public ResponseEntity<String> verifyToken(@RequestParam("token") String token) {
        userService.validateVerificationToken(token);
        return ResponseEntity.ok("Verified");
    }

    private void resendVerifyTokenMail(User user, String applicationUrl, VerificationToken verificationToken) {
        String url = applicationUrl + "/verifyToken?token=" + verificationToken.getToken();
        log.info("Click the link to verify your account: {} ", url);
    }

    private String applicationUrl(HttpServletRequest httpRequest) {
        return "http://"
                + httpRequest.getServerName()
                + ":" + httpRequest.getLocalPort()
                + httpRequest.getContextPath();
    }

}
