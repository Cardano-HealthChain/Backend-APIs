package com.cardano.healthchain.cardano.healthchain.security;

import com.cardano.healthchain.cardano.healthchain.configs.ResidentUserDetailsService;
import com.cardano.healthchain.cardano.healthchain.utils.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {
    private final JwtValidityFilter jwtValidityFilter;
    private final JwtService jwtService;
    public SecurityConfig(JwtValidityFilter jwtValidityFilter, JwtService jwtService) {
        this.jwtValidityFilter = jwtValidityFilter;
        this.jwtService = jwtService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager createAuthenticationManager(HttpSecurity httpSecurity, ResidentUserDetailsService residentUserDetailsService, PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder authManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(residentUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        authManagerBuilder.authenticationProvider(daoAuthenticationProvider);
        return authManagerBuilder.build();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/wallet-auth/link-wallet").authenticated()
                        .requestMatchers("/api/v1/resident/signup","/auth/login", "/api/v1/wallet-auth")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authenticationManager(authenticationManager)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((req, res, e) -> {
                            res.sendError(401, "Unauthorized");
                        })
                )
                .addFilterBefore(new EmailPasswordAuthFilter(authenticationManager,jwtService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtValidityFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
