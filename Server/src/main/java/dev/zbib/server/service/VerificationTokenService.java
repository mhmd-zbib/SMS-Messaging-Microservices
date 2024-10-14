package dev.zbib.server.service;

import dev.zbib.server.exception.Exceptions.BadRequestException;
import dev.zbib.server.model.entity.User;
import dev.zbib.server.model.entity.VerificationToken;
import dev.zbib.server.repository.VerificationTokenRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
@Log4j2
public class VerificationTokenService {

    private static final int EXPIRATION_TIME = 10; // 10 minutes

    private final VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public VerificationTokenService(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    public VerificationToken generateToken(User user) {
        String token = UUID.randomUUID().toString();
        Date expiryDate = calculateExpirationTime();
        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .user(user)
                .expiryTime(expiryDate)
                .build();

        log.info("Token generated: {}", token);
        return verificationTokenRepository.save(verificationToken);

    }

    public VerificationToken regenerateToken(String oldToken) {
        VerificationToken token = getToken(oldToken);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryTime(calculateExpirationTime());

        log.info("Token regenerated: {}", token.getToken());
        return verificationTokenRepository.save(token);
    }

    public VerificationToken validateToken(String oldToken) {
        VerificationToken token = getToken(oldToken);
        checkExpiry(token);
        log.info("Token validated: {}", token.getToken());
        return token;
    }

    private VerificationToken getToken(String token) {
        return verificationTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new BadRequestException("Invalid verification token"));
    }

    private void checkExpiry(VerificationToken token) {
        Calendar calendar = Calendar.getInstance();
        if (token.getExpiryTime().getTime() - calendar.getTime().getTime() <= 0) {
            throw new BadRequestException("Verification token expired");
        }
    }

    private Date calculateExpirationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, EXPIRATION_TIME);
        return new Date(calendar.getTime().getTime());
    }
}
