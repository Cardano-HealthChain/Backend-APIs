package com.cardano.healthchain.cardano.healthchain.utils.medicalData;

import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataResponse;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;

//@Repository
public class MedicalMedicalDataRepositoryImpl implements MedicalDataRepositoryI {
    private final JdbcTemplate jdbcTemplate;
    public MedicalMedicalDataRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public ArrayList<MedicalDataResponse> getMedicalRecordsForUser(int page, String email) {
        int offset = (page - 1) * 5;
        String getMedicalRecordsForUserFilteredSqlStatement = "SELECT o.*, u.* FROM medical_records o JOIN users u on o.user_id = u.user_id WHERE email = ? ORDER BY record_id LIMIT ? offset ?";
        Object[] args = new Object[]{email,5,offset};
        return (ArrayList<MedicalDataResponse>) jdbcTemplate.query(getMedicalRecordsForUserFilteredSqlStatement,new BeanPropertyRowMapper<>(MedicalDataResponse.class),args);
    }
    @Override
    public ArrayList<MedicalDataResponse> getMedicalRecordsForUserFiltered(int page, String email, String category) {
        int offset = (page - 1) * 5;
        String getMedicalRecordsForUserFilteredSqlStatement = "SELECT o.*, u.* FROM medical_records o JOIN users u on o.user_id = u.user_id WHERE email = ? AND record_type = ? ORDER BY record_id LIMIT ? offset ?";
        Object[] args = new Object[]{email,category,5,offset};
        return (ArrayList<MedicalDataResponse>) jdbcTemplate.query(getMedicalRecordsForUserFilteredSqlStatement,new BeanPropertyRowMapper<>(MedicalDataResponse.class),args);
    }
    @Override
    public MedicalDataResponse getMedicalRecordById(String recordId) {
        String getMedicalRecordByIdSqlStatement = "SELECT * FROM medical_records WHERE record_id = ?";
        Object[] args = new Object[]{recordId};
        return jdbcTemplate.queryForObject(getMedicalRecordByIdSqlStatement,new BeanPropertyRowMapper<>(MedicalDataResponse.class),args);
    }
}