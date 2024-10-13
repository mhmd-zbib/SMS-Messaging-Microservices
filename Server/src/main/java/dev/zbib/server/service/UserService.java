package dev.zbib.server.service;

import dev.zbib.server.exception.Exceptions.NotFoundException;
import dev.zbib.server.model.entity.User;
import dev.zbib.server.model.entity.VerificationToken;
import dev.zbib.server.model.enums.UserRole;
import dev.zbib.server.model.request.RegisterRequest;
import dev.zbib.server.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private VerificationTokenService verificationTokenService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, VerificationTokenService verificationTokenService) {
        this.verificationTokenService = verificationTokenService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not exists by Username or Email"));
        UserRole role = user.getRole();
        GrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        return new org.springframework.security.core.userdetails.User(
                usernameOrEmail,
                user.getPassword(),
                Collections.singleton(authority)
        );

    }

    public User registerUser(RegisterRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(false)
                .role(UserRole.USER)
                .build();

        User newUser = userRepository.save(user);
        verificationTokenService.generateToken(newUser);
        return newUser;
    }

    public void verifyUser(String token) {
        VerificationToken userToken = verificationTokenService.validateToken(token);
        User user = userToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        log.info("User verified successfully");
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public void resetPassword(String email) {
        User user = getByEmail(email);
        String token = UUID.randomUUID().toString();

    }
}
