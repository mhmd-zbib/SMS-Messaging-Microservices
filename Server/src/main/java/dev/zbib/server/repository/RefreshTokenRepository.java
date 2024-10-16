package dev.zbib.server.repository;

import dev.zbib.server.model.entity.RefreshToken;
import dev.zbib.server.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    List<RefreshToken> findByUser(User user);

    RefreshToken findByToken(String refreshToken);
}
