package com.cardano.healthchain.cardano.healthchain.security;

import com.cardano.healthchain.cardano.healthchain.user.UserRepositoryImpl;
import com.cardano.healthchain.cardano.healthchain.user.dtos.UserDataProfileResponse;
import com.cardano.healthchain.cardano.healthchain.utils.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtValidityFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepositoryImpl userRepository;
    public JwtValidityFilter(JwtService jwtService, UserRepositoryImpl userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        logger.info("JWT filter hit: {} ".concat(request.getRequestURI()));
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authHeader.substring(7);
        logger.info("JWT found: {} ".concat(token));
        // Validate JWT
        if (!jwtService.isTokenValid(token)) {
            logger.warn("Invalid or expired JWT: " + token);
            filterChain.doFilter(request, response);
            return;
        }
        String user_id = jwtService.extractUserId(token).toString();
        logger.info("JWT is valid for userId= ".concat(user_id));
        // Avoid overwriting existing authentication
        if (user_id != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Fetch user from DB
            UserDataProfileResponse user = userRepository.getUserById(user_id);
            if (user == null) {
                filterChain.doFilter(request, response);
                return;
            }
            // Convert DB role to Spring Security authority
            GrantedAuthority authority = new SimpleGrantedAuthority(
                    "ROLE_".concat(jwtService.extractRole(token)).toUpperCase()
            );
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            user.getUser_id().toString(),
                            null,
                            List.of(authority)
                    );
            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
            // Set authentication
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }
}
