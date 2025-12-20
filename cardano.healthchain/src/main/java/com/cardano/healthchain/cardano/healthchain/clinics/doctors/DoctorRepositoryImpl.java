package com.cardano.healthchain.cardano.healthchain.clinics.doctors;

import com.cardano.healthchain.cardano.healthchain.clinics.doctors.dtos.DoctorDataResponse;
import com.cardano.healthchain.cardano.healthchain.clinics.dtos.DoctorCreateRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class DoctorRepositoryImpl implements DoctorRepositoryI{
    private final JdbcTemplate jdbcTemplate;

    public DoctorRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createDoctor(String clinicId, DoctorCreateRequest doctorCreateRequest) {
        String sql = "INSERT INTO doctors (clinic_id, doctor_email, password, first_name, last_name, dob, gender, address, phone_number) VALUES (?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(
                sql,
                UUID.fromString(clinicId),
                doctorCreateRequest.getDoctor_email(),
                doctorCreateRequest.getDoctor_password(),
                doctorCreateRequest.getDoctor_firstname(),
                doctorCreateRequest.getDoctor_lastname(),
                doctorCreateRequest.getDob(),
                doctorCreateRequest.getGender(),
                doctorCreateRequest.getAddress(),
                doctorCreateRequest.getPhone_number()
        );
    }
    @Override
    public DoctorDataResponse getDoctorById(String doctorId) {
        String sql = "SELECT * FROM doctors WHERE doctor_id = ? LIMIT 1";
        return jdbcTemplate.queryForObject(
                sql,
                new BeanPropertyRowMapper<>(DoctorDataResponse.class),
                UUID.fromString(doctorId)
        );
    }
    @Override
    public DoctorDataResponse getDoctorByEmail(String doctorEmail) {
        String sql = "SELECT * FROM doctors WHERE doctor_email = ? LIMIT 1";
        return jdbcTemplate.queryForObject(
                sql,
                new BeanPropertyRowMapper<>(DoctorDataResponse.class),
                doctorEmail
        );
    }
    @Override
    public int getDoctorsCountUnderClinic(String clinicId) {
        String sql = "SELECT COUNT(*) FROM doctors WHERE clinic_id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                UUID.fromString(clinicId)
        );    }
}