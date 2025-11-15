package com.cardano.healthchain.cardano.healthchain.dashboard;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

//@Repository
public class DashboardRepositoryImpl implements DashboardRepositoryI{
    private final JdbcTemplate jdbcTemplate;

    public DashboardRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
