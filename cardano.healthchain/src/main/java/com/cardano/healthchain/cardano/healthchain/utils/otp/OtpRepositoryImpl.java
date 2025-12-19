package com.cardano.healthchain.cardano.healthchain.utils.otp;

import com.cardano.healthchain.cardano.healthchain.utils.otp.dtos.OtpDataResponse;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class OtpRepositoryImpl implements OtpRepositoryI{
    private final JdbcTemplate jdbcTemplate;
    public OtpRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public OtpDataResponse getOtpByCodeAndEmail(String otpcode, String email) {
        String otpGetByCodeAndEmailSqlStatement = "SELECT * FROM otp_codes WHERE otp_code = ? AND email = ?";
        Object[] args = new Object[]{otpcode,email};
        return jdbcTemplate.queryForObject(
                otpGetByCodeAndEmailSqlStatement,
                new BeanPropertyRowMapper<>(OtpDataResponse.class),
                args
        );
    }
    @Override
    public void insertOtpForUser(String email, String otpcode) {
        String OtpInsertStatement = "INSERT INTO otp_codes (email,otp_code,expires_at) VALUES (?,?,?)";
        Object[] args = new Object[]{email,otpcode, LocalDateTime.now().plusMinutes(10)};
        jdbcTemplate.update(
                OtpInsertStatement,
                args
        );
    }
}
