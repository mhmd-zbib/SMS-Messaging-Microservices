package dev.zbib.server.model.listener;

import dev.zbib.server.event.RegistrationCompleteEvent;
import dev.zbib.server.model.entity.User;
import dev.zbib.server.service.UserService;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
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

        //  Send to the user
    }
}
