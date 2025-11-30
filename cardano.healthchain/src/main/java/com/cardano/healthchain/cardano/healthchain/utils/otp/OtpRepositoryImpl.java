package com.cardano.healthchain.cardano.healthchain.utils.otp;

import org.springframework.jdbc.core.JdbcTemplate;

public class OtpRepositoryImpl implements OtpRepositoryI{
    private final JdbcTemplate jdbcTemplate;
    public OtpRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public OtpResponse getOtpByCodeAndEmail(String code, String user_email) {
        return null;
    }
    @Override
    public void insertOtpForUser(String user_id, String code) {
    }
}
