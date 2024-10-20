package dev.zbib.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.zbib.server.exception.Exceptions.UnAuthorizedException;
import dev.zbib.server.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Log4j2
public class JwtAuthFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws IOException, ServletException {
        try {
            log.debug("Request path: {}", request.getServletPath());

            if (isAuthPath(request)) {
                filterChain.doFilter(request, response);
                return;
            }

            final String token = jwtUtils.extractToken(request);

            final String tokenType = jwtUtils.extractTokenType(token);
            if (!"access".equals(tokenType) && !request.getServletPath().contains("refresh")) {
                throw new JwtException("Invalid token type. Access token required.");
            }

            final String username = jwtUtils.extractUsername(token);
            log.debug("Extracted username: {}", username);

            authenticateUser(request, username, token);
            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException | SignatureException | MalformedJwtException | UnAuthorizedException ex) {
            handleException(response, HttpServletResponse.SC_UNAUTHORIZED, "Token error. Please check your token.", ex);
        } catch (InsufficientAuthenticationException | AccessDeniedException ex) {
            handleException(response, HttpServletResponse.SC_FORBIDDEN, "Access denied. You do not have permission to access this resource.", ex);
        } catch (LockedException | CredentialsExpiredException ex) {
            handleException(response, HttpServletResponse.SC_UNAUTHORIZED, "Authentication error. Please log in again.", ex);
        } catch (Exception ex) {
            handleException(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request. Please try again later.", ex);
        }
    }


    private void authenticateUser(HttpServletRequest request, String username, String token) {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtUtils.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
    }

    private boolean isAuthPath(HttpServletRequest request) {
        return request.getServletPath().contains("/auth/login") || request.getServletPath().contains("/auth/register");
    }

    private void handleException(HttpServletResponse response, int status, String message, Exception ex) throws IOException {
        log.error("{}: {}", message, ex != null ? ex.getMessage() : "No exception");
        response.setStatus(status);
        sendErrorResponse(response, message);
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", message);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
    }

}
