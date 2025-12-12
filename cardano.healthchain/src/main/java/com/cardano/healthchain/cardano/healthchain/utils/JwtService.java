package com.cardano.healthchain.cardano.healthchain.utils;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long jwtExpirationMs;

    public JwtService(Dotenv dotenv) {
        this.secretKey = Keys.hmacShaKeyFor(dotenv.get("JWT_SECRET").getBytes());
        this.jwtExpirationMs = Long.parseLong(dotenv.get("JWT_EXPIRATION_MS"));
    }

    // ===============================
    // 1. Generate JWTs
    // ===============================

    // For wallet-only users (no email)
    public String generateTokenWithUserId(String userId, String role, Map<String, Object> extraClaims) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userId)   // Primary identity
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // For email/password users (or if email is available)
    public String generateTokenWithUserIdAndEmail(String userId, String email, String role, Map<String, Object> extraClaims) {
        return Jwts.builder()
                .setSubject(userId)   // Primary identity
                .claim("role", role)
                .claim("email", email)
                .setClaims(extraClaims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // ===============================
    // 2. Extract data from JWT
    // ===============================

    // Extract user ID (UUID) — always present
    public UUID extractUserId(String token) {
        String subject = extractClaim(token, Claims::getSubject);
        return UUID.fromString(subject);
    }

    // Extract email (nullable)
    public String extractEmail(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("email", String.class);  // null if wallet login
    }

    // Extract role
    public String extractRole(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("role", String.class);
    }

    // Extract expiration date
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Generic claim extraction
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // ===============================
    // 3. Validate token
    // ===============================

    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // ===============================
    // Internal helper
    // ===============================
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}