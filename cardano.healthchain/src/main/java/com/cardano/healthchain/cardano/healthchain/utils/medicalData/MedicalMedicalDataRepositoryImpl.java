package com.cardano.healthchain.cardano.healthchain.utils.medicalData;

import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataResponse;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class MedicalMedicalDataRepositoryImpl implements MedicalDataRepositoryI {
    private final JdbcTemplate jdbcTemplate;
    public MedicalMedicalDataRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public ArrayList<MedicalDataResponse> getMedicalRecordsForUser(int page, String email) {
        int pageNumber = Math.max(page, 1); // at least 1
        int offset = (pageNumber - 1) * 5;
        String getMedicalRecordsForUserFilteredSqlStatement = "SELECT o.*, u.* FROM medical_records o JOIN users u on o.user_email = u.email WHERE email = ? ORDER BY record_id LIMIT ? offset ?";
        Object[] args = new Object[]{email,5,offset};
        return (ArrayList<MedicalDataResponse>) jdbcTemplate.query(getMedicalRecordsForUserFilteredSqlStatement,new BeanPropertyRowMapper<>(MedicalDataResponse.class),args);
    }
    @Override
    public ArrayList<MedicalDataResponse> getMedicalRecordsForUserFiltered(int page, String email, String category) {
        int pageNumber = Math.max(page, 1); // at least 1
        int offset = (pageNumber - 1) * 5;
        String getMedicalRecordsForUserFilteredSqlStatement = "SELECT o.*, u.* FROM medical_records o JOIN users u on o.user_email = u.email WHERE email = ?  AND record_type = ? ORDER BY record_id LIMIT ? offset ?";
        Object[] args = new Object[]{email,category,5,offset};
        return (ArrayList<MedicalDataResponse>) jdbcTemplate.query(getMedicalRecordsForUserFilteredSqlStatement,new BeanPropertyRowMapper<>(MedicalDataResponse.class),args);
    }
    @Override
    public MedicalDataResponse getMedicalRecordById(String recordId) {
        String getMedicalRecordByIdSqlStatement = "SELECT * FROM medical_records WHERE record_id = ?";
        Object[] args = new Object[]{recordId};
        return jdbcTemplate.queryForObject(getMedicalRecordByIdSqlStatement,new BeanPropertyRowMapper<>(MedicalDataResponse.class),args);
    }
    @Override
    public int getVerifiedRecordCountForUser(String user_id) {
        String getVerifiedRecordCountSqlStatement = "SELECT COUNT(*) FROM medical_records where user_id = ? and verified = TRUE";
        return jdbcTemplate.queryForObject(getVerifiedRecordCountSqlStatement,Integer.class,new Object[]{user_id} );
    }
    @Override
    public void recordPermissionSharedWithClinic(String email, String clinicId) {
        String recordPermissionSharedWithClinicSqlStatement = "INSERT INTO medical_records_shared_with (user_email, clinic_id) VALUES (?,?)";
        Object[] args = new Object[]{email,clinicId};
        jdbcTemplate.update(recordPermissionSharedWithClinicSqlStatement,args);
    }
}