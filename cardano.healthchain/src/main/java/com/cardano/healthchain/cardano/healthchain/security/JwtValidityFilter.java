package com.cardano.healthchain.cardano.healthchain.security;

import com.cardano.healthchain.cardano.healthchain.user.UserRepositoryI;
import com.cardano.healthchain.cardano.healthchain.user.UserRepositoryImpl;
import com.cardano.healthchain.cardano.healthchain.user.dtos.UserModel;
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

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        // Validate JWT
        if (!jwtService.isTokenValid(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        String email = jwtService.extractUsername(token);

        // Avoid overwriting existing authentication
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Fetch user from DB
            UserModel user = userRepository.getUserByEmail(email);
            if (user == null) {
                filterChain.doFilter(request, response);
                return;
            }
            // Convert DB role to Spring Security authority
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase());
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
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
