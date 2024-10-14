package dev.zbib.server.controller;

import dev.zbib.server.service.AuthService;
import dev.zbib.server.service.UserService;
import dev.zbib.server.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;
    private final VerificationTokenService verificationTokenService;
    private final AuthService authService;


}
