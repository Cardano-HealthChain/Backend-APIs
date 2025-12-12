package com.cardano.healthchain.cardano.healthchain.security;

import com.cardano.healthchain.cardano.healthchain.utils.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;
public class EmailPasswordAuthFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public EmailPasswordAuthFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        setFilterProcessesUrl("/auth/login"); // POST /auth/login for logging in with emails
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Map<String, String> requestBody = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            String email = requestBody.get("email");
            String password = requestBody.get("password");
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);
            return authenticationManager.authenticate(authToken);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
            throw new RuntimeException("Invalid login payload");
        }
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult
    ) throws IOException {
        String user_id = authResult.getName();
        String role = authResult.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)  // extract the authority string
                .findFirst()
                .orElse(null);
        String token = jwtService.generateTokenWithUserId(
                user_id,
                role,
                Map.of()
        );
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(token, authResult.getAuthorities().stream().findFirst().orElse(null).toString().toLowerCase());
        String responseLoginDTO = new ObjectMapper().writeValueAsString(loginResponseDTO);
        logger.info(String.format("login attempt made for: %s and response was: %s",user_id,responseLoginDTO));
        response.getWriter().write(responseLoginDTO);
    }
}