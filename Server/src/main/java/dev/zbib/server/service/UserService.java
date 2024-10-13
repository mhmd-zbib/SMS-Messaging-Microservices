package dev.zbib.server.service;

import dev.zbib.server.model.entity.User;
import dev.zbib.server.model.entity.VerificationToken;
import dev.zbib.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final VerificationTokenService verificationTokenService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

//    public void verifyUser(String token) {
//        VerificationToken userToken = verificationTokenService.validateToken(token);
//        User user = userToken.getUser();
//        user.setEnabled(true);
//        userRepository.save(user);
//        log.info("User verified successfully");
//    }
}
