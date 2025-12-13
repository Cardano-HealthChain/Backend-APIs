package com.cardano.healthchain.cardano.healthchain.utils.medicalData;

import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataResponse;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public class MedicalMedicalDataRepositoryImpl implements MedicalDataRepositoryI {
    private final JdbcTemplate jdbcTemplate;
    public MedicalMedicalDataRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public ArrayList<MedicalDataResponse> getMedicalRecordsForUser(int page, String user_id) {
        int pageNumber = Math.max(page, 1); // at least 1
        int offset = (pageNumber - 1) * 5;
        String getMedicalRecordsForUserFilteredSqlStatement = "SELECT o.*, u.* FROM medical_records o JOIN users u on o.user_id = u.user_id WHERE user_id = ? ORDER BY record_id LIMIT ? offset ?";
        Object[] args = new Object[]{UUID.fromString(user_id),5,offset};
        return (ArrayList<MedicalDataResponse>) jdbcTemplate.query(getMedicalRecordsForUserFilteredSqlStatement,new BeanPropertyRowMapper<>(MedicalDataResponse.class),args);
    }
    @Override
    public ArrayList<MedicalDataResponse> getMedicalRecordsForUserFiltered(int page, String user_id, String category) {
        int pageNumber = Math.max(page, 1); // at least 1
        int offset = (pageNumber - 1) * 5;
        String getMedicalRecordsForUserFilteredSqlStatement = "SELECT o.*, u.* FROM medical_records o JOIN users u on o.user_id = u.user_id WHERE user_id = ? AND record_type = ? ORDER BY record_id LIMIT ? offset ?";
        Object[] args = new Object[]{UUID.fromString(user_id),category,5,offset};
        return (ArrayList<MedicalDataResponse>) jdbcTemplate.query(getMedicalRecordsForUserFilteredSqlStatement,new BeanPropertyRowMapper<>(MedicalDataResponse.class),args);
    }
    @Override
    public MedicalDataResponse getMedicalRecordById(String recordId) {
        String getMedicalRecordByIdSqlStatement = "SELECT * FROM medical_records WHERE record_id = ?";
        Object[] args = new Object[]{UUID.fromString(recordId)};
        return jdbcTemplate.queryForObject(getMedicalRecordByIdSqlStatement,new BeanPropertyRowMapper<>(MedicalDataResponse.class),args);
    }
    @Override
    public int getVerifiedRecordCountForUser(String user_id) {
        String getVerifiedRecordCountSqlStatement = "SELECT COUNT(*) FROM medical_records where user_id = ? and verified = TRUE";
        return jdbcTemplate.queryForObject(getVerifiedRecordCountSqlStatement,Integer.class,new Object[]{UUID.fromString(user_id)} );
    }
    @Override
    public void recordPermissionSharedWithClinic(String user_id, String clinicId) {
        String recordPermissionSharedWithClinicSqlStatement = "INSERT INTO medical_records_shared_with (user_id, clinic_id) VALUES (?,?)";
        Object[] args = new Object[]{UUID.fromString(user_id),clinicId};
        jdbcTemplate.update(recordPermissionSharedWithClinicSqlStatement,args);
    }
}