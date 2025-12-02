package com.cardano.healthchain.cardano.healthchain.utils.sessions;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }
    public void createSession(String email, String refreshToken, String ip, String agent) {

        SessionModel session = new SessionModel();
        session.setUserEmail(email);
        session.setRefreshToken(refreshToken);
        session.setIpAddress(ip);
        session.setUserAgent(agent);
        session.setCreatedAt(LocalDateTime.now());
        session.setExpiresAt(LocalDateTime.now().plusDays(30)); // 30-day validity
        sessionRepository.save(session);
    }
    public List<SessionModel> listSessions(String email) {
        return sessionRepository.getSessionsForUser(email);
    }
    public void revokeSession(UUID sessionId) {
        sessionRepository.revokeSession(sessionId);
    }
    public void revokeAllSessions(String email) {
        sessionRepository.revokeAll(email);
    }
}