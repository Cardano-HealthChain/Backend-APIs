package com.cardano.healthchain.cardano.healthchain.utils.medicalData;

import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataResponse;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataUploadRequest;
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
    public ArrayList<MedicalDataResponse> getMedicalRecordsForUser(String userId, int page) {
        int offset;
        if(page < 1) page = 1;
        offset = (page - 1) * 10;
        String sql = "SELECT record_id, record_type, record_data, hash_local, blockchain_tx_id AS blockchainTransactionID, verified, clinic_uploaded, doctor_uploaded, created_at FROM medical_records WHERE user_id = ? ORDER BY created_at DESC LIMIT 10 OFFSET ?";
        return (ArrayList<MedicalDataResponse>) jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(MedicalDataResponse.class),
                UUID.fromString(userId),
                offset
        );
    }
    @Override
    public ArrayList<MedicalDataResponse> getMedicalRecordsForUserFiltered(String userId, String category, int page) {
        int offset;
        if(page < 1) page = 1;
        offset = (page - 1) * 10;
        String sql = "SELECT record_id, record_type, record_data, hash_local, blockchain_tx_id AS blockchainTransactionID, verified, clinic_uploaded, doctor_uploaded, created_at FROM medical_records WHERE user_id = ? AND record_type = ? ORDER BY created_at DESC LIMIT 10 OFFSET ?";
        return (ArrayList<MedicalDataResponse>) jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(MedicalDataResponse.class),
                UUID.fromString(userId),
                category,
                offset
        );
    }
    @Override
    public MedicalDataResponse getMedicalRecordById(String recordId) {
        String sql = "SELECT record_id, record_type, record_data, hash_local, blockchain_tx_id AS blockchainTransactionID, verified, clinic_uploaded, doctor_uploaded, created_at FROM medical_records WHERE record_id = ? LIMIT 1 ";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(MedicalDataResponse.class),UUID.fromString(recordId));
    }
    @Override
    public int getVerifiedRecordCountForUser(String userId) {
        String sql = "SELECT COUNT(*) FROM medical_records WHERE user_id = ? AND verified = TRUE";
        return jdbcTemplate.queryForObject(sql, Integer.class, UUID.fromString(userId));
    }
    @Override
    public void uploadMedicalDataForUser(MedicalDataUploadRequest medicalDataUploadRequest, String doctorId, UUID clinicId, String userId, String hashLocalValue, String blockchainTxId, String blockchainTxHash, String blockNumber, String doctorUploaded, String clinicUploaded) {
        String sql = "INSERT INTO medical_records (user_id, clinic_id, record_type, record_data, hash_local, blockchain_tx_id, blockchain_tx_hash, block_number, doctor_uploaded, clinic_uploaded, verified, diagnosis) VALUES (?,?,?,?,?,?,?,?,?,?,TRUE,?)";
        jdbcTemplate.update(
                sql,
                UUID.fromString(userId),
                clinicId,
                medicalDataUploadRequest.getRecord_type(),
                medicalDataUploadRequest.getRecord_data(),
                hashLocalValue,
                blockchainTxId,
                blockchainTxHash,
                blockNumber,
                doctorUploaded,
                clinicUploaded,
                medicalDataUploadRequest.getDiagnosis()
        );
    }

    @Override
    public void recordPermissionSharedWithClinic(String userId, String clinicId) {
        String sql = "INSERT INTO medical_records_shared_with (user_id, clinic_id) VALUES (?,?) ON CONFLICT DO NOTHING";
        jdbcTemplate.update(sql,UUID.fromString(userId),UUID.fromString(clinicId));
    }
}