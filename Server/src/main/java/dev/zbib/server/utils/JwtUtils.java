package dev.zbib.server.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * <h2>JWT util service</h2>
 * <p>This service handles everything related to JWT configurations</p>
 */
@Service
public class JwtUtils {

    @Value("${app.jwt.secret}")
    private String secretKey;


    private long accessExpiration = 15 ; // 15 minutes

    private long refreshExpiration = 7 ; // 7 days


    /**
     * <h2>Generate base JWT</h2>
     * <p>This method is a general way of create JWT, we can control it to set what type of JWT we need and the usage of it</p>
     */
    public String generateToken(Map<String, Object> extraClaims,
                                UserDetails userDetails,
                                long expiration) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * <h2>Generate access token</h2>
     * <p>This method connects to {@generateToken} by that it automatically passes the expiration date</p>
     */
    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, accessExpiration);
    }

    /**
     * <h2>Generate refresh token</h2>
     * <p>Same as {@generateAccessToken}</p>
     */
    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    /**
     * <h2>Extracts the username from the JWT bearer</h2>
     * <p>This method takes the jwt token and uses the {@extractClaim} to get the username that is connected with this token</p>
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * <h2>Validates the token</h2>
     * <p>This method gets the date and the user of the JWT and makes sure everything is set</p>
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

