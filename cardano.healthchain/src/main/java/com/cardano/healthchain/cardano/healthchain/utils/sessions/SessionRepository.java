package com.cardano.healthchain.cardano.healthchain.utils.sessions;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class SessionRepository {
    private final JdbcTemplate jdbcTemplate;

    public SessionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(SessionModel session) {
        String sql = """
            INSERT INTO user_sessions
            (user_email, refresh_token, ip_address, user_agent, expires_at)
            VALUES (?, ?, ?, ?, ?)
        """;

        jdbcTemplate.update(
                sql,
                session.getUserEmail(),
                session.getRefreshToken(),
                session.getIpAddress(),
                session.getUserAgent(),
                session.getExpiresAt()
        );
    }

    public List<SessionModel> getSessionsForUser(String email) {
        String sql = "SELECT * FROM user_sessions WHERE user_email = ? AND revoked = FALSE";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SessionModel.class), email);
    }

    public SessionModel getByRefreshToken(String token) {
        String sql = "SELECT * FROM user_sessions WHERE refresh_token = ? AND revoked = FALSE";
        List<SessionModel> result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SessionModel.class), token);

        return result.isEmpty() ? null : result.get(0);
    }

    public void revokeSession(UUID sessionId) {
        String sql = "UPDATE user_sessions SET revoked = TRUE WHERE session_id = ?";
        jdbcTemplate.update(sql, sessionId);
    }

    public void revokeAll(String email) {
        String sql = "UPDATE user_sessions SET revoked = TRUE WHERE user_email = ?";
        jdbcTemplate.update(sql, email);
    }
}
