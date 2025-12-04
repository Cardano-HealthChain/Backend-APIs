package com.cardano.healthchain.cardano.healthchain.clinics;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ClinicRepositoryImpl implements ClinicRepositoryI{
    private final JdbcTemplate jdbcTemplate;

    public ClinicRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int getTotalClinicsVisitedByUser(String user_email) {
        String getTotalClinicsVisitedByUserSqlStatement = "SELECT COUNT(*) FROM medical_records_shared_with where user_email = ?";
        return jdbcTemplate.queryForObject(getTotalClinicsVisitedByUserSqlStatement,Integer.class,new Object[]{user_email} );
    }
}
