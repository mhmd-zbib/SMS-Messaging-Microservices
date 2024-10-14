package dev.zbib.server.service;

import dev.zbib.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Log4j2
public class UserService implements UserDetailsService {


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
