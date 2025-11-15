package com.cardano.healthchain.cardano.healthchain.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

//@Repository
public class DataRepositoryImpl implements DataRepositoryI{
    private final JdbcTemplate jdbcTemplate;

    public DataRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
