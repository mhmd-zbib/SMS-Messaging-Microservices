package dev.zbib.server.repository;

import dev.zbib.server.model.entity.JwtToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface JwtTokenRepository extends CrudRepository<JwtToken, String> {

    @Query(value = """
            select t from JwtToken t inner join User u\s
            on t.user.id = u.id\s
            where u.id = :id and (t.expired = false or t.revoked = false)\s
            """)
    List<JwtToken> findAllValidTokenByUser(Integer id);

    Optional<JwtToken> findByToken(String token);
}
