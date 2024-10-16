package dev.zbib.server.service;

import dev.zbib.server.model.entity.RefreshToken;
import dev.zbib.server.model.entity.User;
import dev.zbib.server.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public void saveUserRefreshToken(User user,
                                     String refreshToken) {
        RefreshToken token = RefreshToken.builder()
                .token(refreshToken)
                .user(user)
                .isRevoked(false)
                .build();
        refreshTokenRepository.save(token);
    }

    public void revokeAllUserRefreshTokens(User user) {
        List<RefreshToken> userRefreshTokens = refreshTokenRepository.findByUser(user);
        for (RefreshToken t : userRefreshTokens) {
            t.setRevoked(true);
        }
        refreshTokenRepository.saveAll(userRefreshTokens);
    }

    public void revokeRefreshToken(String refreshToken) {
        RefreshToken userToken = refreshTokenRepository.findByToken(refreshToken);
        userToken.setRevoked(true);
        refreshTokenRepository.save(userToken);
    }

}
