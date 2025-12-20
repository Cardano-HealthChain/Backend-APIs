package com.cardano.healthchain.cardano.healthchain.clinics;

import com.cardano.healthchain.cardano.healthchain.clinics.dtos.ClinicAdminCreateRequest;
import com.cardano.healthchain.cardano.healthchain.clinics.dtos.ClinicCreateRequest;
import com.cardano.healthchain.cardano.healthchain.clinics.dtos.ClinicDataResponse;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ClinicRepositoryImpl implements ClinicRepositoryI{
    private final JdbcTemplate jdbcTemplate;
    public ClinicRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public int getTotalClinicsVisitedByUser(String userId) {
        String getTotalClinicsVisitedByUserSqlStatement = "SELECT COUNT(*) FROM medical_records_shared_with WHERE user_id = ?";
        return jdbcTemplate.queryForObject(getTotalClinicsVisitedByUserSqlStatement,Integer.class,UUID.fromString(userId));
    }
    @Override
    public UUID createClinic(ClinicCreateRequest clinicCreateRequest) {
        String sql = "INSERT INTO clinics (clinic_name, clinic_facility_type, clinic_registration_number, clinic_email, clinic_phone_number, clinic_address, clinic_region, clinic_lga) VALUES (?,?,?,?,?,?,?,?)  RETURNING clinic_id";
        Object[] args = new Object[]{
                clinicCreateRequest.getClinic_name(),
                clinicCreateRequest.getFacility_type(),
                clinicCreateRequest.getRegistration_number(),
                clinicCreateRequest.getClinic_email(),
                clinicCreateRequest.getClinic_phone_number(),
                clinicCreateRequest.getClinic_address(),
                clinicCreateRequest.getRegion(),
                clinicCreateRequest.getLga()
        };
        return jdbcTemplate.queryForObject(
                sql,
                args,
                UUID.class
        );
    }
    @Override
    public void updateClinicRegion(String clinicId, String newRegion) {
        String sql = "UPDATE clinics SET clinic_region = ? WHERE clinic_id = ?";
        jdbcTemplate.update(sql, newRegion, UUID.fromString(clinicId));
    }
    @Override
    public void updateAdminDetails(ClinicAdminCreateRequest clinicAdminCreateRequest, String clinicId) {
        String sql = "UPDATE clinics SET clinic_admin_name = ?, clinic_admin_email = ?, clinic_admin_phone_number = ?, clinic_admin_password = ? WHERE clinic_id = ?";
        jdbcTemplate.update(
                sql,
                clinicAdminCreateRequest.getAdmin_name(),
                clinicAdminCreateRequest.getAdmin_email_address(),
                clinicAdminCreateRequest.getPhone_number(),
                clinicAdminCreateRequest.getPassword(),
                UUID.fromString(clinicId)
        );
    }
    @Override
    public ClinicDataResponse getClinicById(String clinic_id) {
        String sql = "SELECT * FROM clinics WHERE clinic_id = ? LIMIT 1";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ClinicDataResponse.class), UUID.fromString(clinic_id));
    }
    @Override
    public ClinicDataResponse getClinicByEmail(String clinic_admin_email) {
        String sql = "SELECT * FROM clinics WHERE clinic_email = ? LIMIT 1";
        Object[] args = new Object[]{clinic_admin_email};
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(ClinicDataResponse.class),args);
    }
}