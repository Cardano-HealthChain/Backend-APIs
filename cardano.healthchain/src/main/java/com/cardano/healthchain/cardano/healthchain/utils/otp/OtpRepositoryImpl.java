package com.cardano.healthchain.cardano.healthchain.utils.otp;

import com.cardano.healthchain.cardano.healthchain.utils.otp.dtos.OtpResponse;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Repository
public class OtpRepositoryImpl implements OtpRepositoryI{
    private final JdbcTemplate jdbcTemplate;
    public OtpRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public OtpResponse getOtpByCodeAndEmail(String otpcode, String email) {
        String otpGetByCodeAndEmailSqlStatement = "SELECT * FROM otp_codes WHERE otp_code = ? AND email = ?";
        Object[] args = new Object[]{otpcode,email};
        return jdbcTemplate.queryForObject(
                otpGetByCodeAndEmailSqlStatement,
                new BeanPropertyRowMapper<>(OtpResponse.class),
                args
        );
    }
    @Override
    public void insertOtpForUser(String email, String otpcode) {
        String OtpInsertStatement = "INSERT INTO otp_codes (email,otp_code,expires_at) VALUES (?,?,?)";
        Object[] args = new Object[]{email,otpcode, Instant.now().plus(10, ChronoUnit.MINUTES)};
        jdbcTemplate.update(
                OtpInsertStatement,
                args
        );
    }
}
