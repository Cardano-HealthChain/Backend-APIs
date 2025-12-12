package com.cardano.healthchain.cardano.healthchain.clinics;

import com.cardano.healthchain.cardano.healthchain.clinics.dtos.ClinicModel;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ClinicRepositoryImpl implements ClinicRepositoryI{
    private final JdbcTemplate jdbcTemplate;

    public ClinicRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int getTotalClinicsVisitedByUser(String user_id) {
        String getTotalClinicsVisitedByUserSqlStatement = "SELECT COUNT(*) FROM medical_records_shared_with WHERE user_id = ?";
        return jdbcTemplate.queryForObject(getTotalClinicsVisitedByUserSqlStatement,Integer.class,new Object[]{user_id} );
    }
    @Override
    public ClinicModel getClinicById(String clinic_id) {
        ClinicModel clinicModel;
        String getClinicByIdSqlStatement = "";
        return jdbcTemplate.queryForObject(getClinicByIdSqlStatement, new BeanPropertyRowMapper<>(ClinicModel.class), new Object[]{clinic_id});
    }
}
