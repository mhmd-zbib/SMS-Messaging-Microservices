package dev.zbib.server.service;

import dev.zbib.server.exception.Exceptions.UnAuthorizedException;
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

    public RefreshToken getByRefreshToken(String token) {
        return refreshTokenRepository.findByRefreshToken(token)
                .orElseThrow(() -> new UnAuthorizedException("Token expired please login"));
    }

    public RefreshToken createToken(String refreshToken, User user) {
        RefreshToken token = RefreshToken.builder()
                .refreshToken(refreshToken)
                .user(user)
                .build();
        return token;
    }

    public void revokeAllUserRefreshTokens(User user) {
        List<RefreshToken> userRefreshTokens = refreshTokenRepository.findAllByUserId(user.getId());
        if (!userRefreshTokens.isEmpty()) {
            refreshTokenRepository.deleteAll(userRefreshTokens);
        }
    }
}
