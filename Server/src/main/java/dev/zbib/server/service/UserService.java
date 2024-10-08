package dev.zbib.server.service;

import dev.zbib.server.exception.Exceptions.BadRequestException;
import dev.zbib.server.model.entity.User;
import dev.zbib.server.model.entity.VerificationToken;
import dev.zbib.server.model.enums.UserRole;
import dev.zbib.server.model.request.RegisterRequest;
import dev.zbib.server.repository.UserRepository;
import dev.zbib.server.repository.VerificationTokenRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, VerificationTokenRepository verificationTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    public User registerUser(RegisterRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(false)
                .role(UserRole.USER)
                .build();

        return userRepository.save(user);
    }

    public void saveVerificationToken(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);
    }

    public void validateVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new BadRequestException("Invalid verification token"));
        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();

        if (verificationToken.getExpiryTime().getTime() - calendar.getTime().getTime() <= 0) {
            verificationTokenRepository.delete(verificationToken);
            throw new BadRequestException("Verification token expired");
        }

        user.setEnabled(true);
        userRepository.save(user);
    }
}
