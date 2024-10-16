package dev.zbib.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.zbib.server.exception.Exceptions.NotFoundException;
import dev.zbib.server.exception.Exceptions.UnAuthorizedException;
import dev.zbib.server.model.entity.RefreshToken;
import dev.zbib.server.model.entity.User;
import dev.zbib.server.model.enums.UserRole;
import dev.zbib.server.model.request.LoginRequest;
import dev.zbib.server.model.request.RegisterRequest;
import dev.zbib.server.model.response.AuthResponse;
import dev.zbib.server.repository.UserRepository;
import dev.zbib.server.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Log4j2
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    /**
     * <h2>User register service with JWT</h2>
     * <p>This service creates a new user and authenticate them with jwt</p>
     */
    public AuthResponse register(RegisterRequest request) {
        User user = userRepository.save(
                User.builder()
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .username(request.getUsername())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .email(request.getEmail())
                        .role(UserRole.USER)
                        .build()
        );
        return generateUserTokens(user);
    }

    /**
     * <h2>Login user with auth</h2>
     * <p>User login that validates the credentials and returns a jwt with the username</p>
     */
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("not found"));
        return generateUserTokens(user);
    }

    /**
     * <h2>Creates another token from the access token</h2>
     * <p>This service takes the jwt and from the old one it creates an access token</p>
     */
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final RefreshToken refreshTokenFromDb;
        final String userName;

        String refreshToken = extractRefreshToken(request);
        userName = jwtUtils.extractUsername(refreshToken);
        refreshTokenFromDb = refreshTokenService.getByRefreshToken(refreshToken);

        if (refreshTokenFromDb == null) {
            throw new UnAuthorizedException("unauthorized");
        }

        if (userName != null) {
            var user = userRepository.findByUsername(userName)
                    .orElseThrow(() -> new UnAuthorizedException("User not found"));
            if (jwtUtils.isTokenValid(refreshToken, user)) {
                var accessToken = jwtUtils.generateAccessToken(user);
                var authResponse = AuthResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public void logoutAllDevices(HttpServletRequest request,
                                 HttpServletResponse response) {
        String refreshToken = extractRefreshToken(request);
        User user = refreshTokenService.getByRefreshToken(refreshToken).getUser();
        refreshTokenService.revokeAllUserRefreshTokens(user);
    }

    private String extractRefreshToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new IllegalArgumentException("Invalid Authorization header");
    }

    private AuthResponse generateUserTokens(User user) {
        String jwtToken = jwtUtils.generateAccessToken(user);
        String refreshToken = jwtUtils.generateRefreshToken(user);
        refreshTokenService.saveUserRefreshToken(user, refreshToken);
        log.info("Logged in user: {} ", user.getEmail());
        return AuthResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

}

