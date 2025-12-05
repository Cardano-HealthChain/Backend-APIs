package com.cardano.healthchain.cardano.healthchain.clinics;

import com.cardano.healthchain.cardano.healthchain.clinics.dtos.ClinicModel;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.function.Consumer;

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
    @Override
    public ClinicModel getClinicById(String clinic_id) {
        ClinicModel clinicModel;
        String getClinicByIdSqlStatement = "";
        return jdbcTemplate.queryForObject(getClinicByIdSqlStatement, new BeanPropertyRowMapper<>(ClinicModel.class), new Object[]{clinic_id});
    }
}
