package dev.zbib.server.model.listener;

import dev.zbib.server.event.RegistrationCompleteEvent;
import dev.zbib.server.model.entity.User;
import dev.zbib.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class RegistrationCompleteListener implements
        ApplicationListener<RegistrationCompleteEvent> {

    private final UserService userService;

    public RegistrationCompleteListener(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveVerificationToken(token, user);
        String url = event.getApplicationUrl() + "verify?token=" + token;

        //this should send the token to the email instead
        log.info("Sending verification email to {}", url);

    }
}
