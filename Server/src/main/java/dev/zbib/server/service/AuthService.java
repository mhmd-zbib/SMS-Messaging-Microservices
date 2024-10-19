package dev.zbib.server.service;

import dev.zbib.server.exception.Exceptions.NotFoundException;
import dev.zbib.server.exception.Exceptions.UnAuthorizedException;
import dev.zbib.server.model.entity.User;
import dev.zbib.server.model.enums.UserRole;
import dev.zbib.server.model.request.LoginRequest;
import dev.zbib.server.model.request.RegisterRequest;
import dev.zbib.server.model.response.AuthResponse;
import dev.zbib.server.repository.UserRepository;
import dev.zbib.server.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Log4j2
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

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

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("not found"));
        return generateUserTokens(user);
    }

    public void logout(HttpServletRequest request) {
        String token = jwtUtils.extractToken(request);
        refreshTokenService.revokeRefreshToken(token);
    }

    public void logoutAllDevices(HttpServletRequest request) {
        String token = jwtUtils.extractToken(request);
        String username = jwtUtils.extractUsername(token);
        User user = userService.getByUsername(username);
        refreshTokenService.revokeAllUserRefreshTokens(user);
    }

    public AuthResponse refreshToken(HttpServletRequest request) {
        String token = jwtUtils.extractToken(request);
        if (refreshTokenService.isTokenRevoked(token)) {
            throw new UnAuthorizedException("Unauthorized, refresh token revoked");
        }
        String username = jwtUtils.extractUsername(token);
        User user = userService.getByUsername(username);
        refreshTokenService.revokeRefreshToken(token);
        return generateUserTokens(user);
    }

    private AuthResponse generateUserTokens(User user) {
        String jwtToken = jwtUtils.generateAccessToken(user);
        String refreshToken = jwtUtils.generateRefreshToken(user);

        refreshTokenService.saveUserRefreshToken(user, refreshToken);
        log.info("authenticated user: {} ", user.getEmail());
        return AuthResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
}

