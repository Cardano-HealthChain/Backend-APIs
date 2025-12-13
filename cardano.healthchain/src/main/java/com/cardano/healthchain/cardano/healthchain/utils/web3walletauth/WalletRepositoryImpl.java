package com.cardano.healthchain.cardano.healthchain.utils.web3walletauth;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class WalletRepositoryImpl implements WalletRepositoryI{
    private final JdbcTemplate jdbcTemplate;

    public WalletRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveChallenge(String walletAddress, String challenge, LocalDateTime expiresAt) {
        String sql = "INSERT INTO wallet_login_challenges (wallet_address, challenge, expires_at) VALUES (?, ?, ?) ON CONFLICT (wallet_address) DO UPDATE SET challenge = EXCLUDED.challenge, expires_at = EXCLUDED.expires_at";
        jdbcTemplate.update(sql, walletAddress, challenge, expiresAt);
    }
    public Optional<String> getChallenge(String walletAddress) {
        String sql = "SELECT challenge FROM wallet_login_challenges WHERE wallet_address = ? AND expires_at > ?";
        try {
            String challenge = jdbcTemplate.queryForObject(sql, String.class, new Object[]{walletAddress,LocalDateTime.now()});
            return Optional.ofNullable(challenge);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    public void deleteChallenge(String walletAddress) {
        String sql = "DELETE FROM wallet_login_challenges WHERE wallet_address = ?";
        jdbcTemplate.update(sql, walletAddress);
    }
}
