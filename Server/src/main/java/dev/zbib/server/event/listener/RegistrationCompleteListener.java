package dev.zbib.server.event.listener;

import dev.zbib.server.event.RegistrationCompleteEvent;
import dev.zbib.server.model.entity.User;
import dev.zbib.server.service.UserService;
import dev.zbib.server.service.VerificationTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class RegistrationCompleteListener implements
        ApplicationListener<RegistrationCompleteEvent> {

    private final UserService userService;
    private final VerificationTokenService verificationTokenService;

    public RegistrationCompleteListener(UserService userService, VerificationTokenService verificationTokenService) {
        this.userService = userService;
        this.verificationTokenService = verificationTokenService;
    }

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
//        verificationTokenService.saveVerificationToken(token, user);
        String url = event.getApplicationUrl() + "/user/verifyToken?token=" + token;

        //this should send the token to the email instead
        log.info("Sending verification email to {}", url);

    }
}
