package dev.zbib.server.repository;

import dev.zbib.server.model.entity.RefreshToken;
import dev.zbib.server.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String token);

    List<RefreshToken> findByUser(User user);

    List<RefreshToken> findAllByUserId(Long userId);

}
