package com.cardano.healthchain.cardano.healthchain.utils.medicalData;

import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataResponse;
import org.springframework.jdbc.core.JdbcTemplate;

//@Repository
public class MedicalMedicalDataRepositoryImpl implements MedicalDataRepositoryI {
    private final JdbcTemplate jdbcTemplate;
    public MedicalMedicalDataRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public MedicalDataResponse getMedicalRecordForUser(int page, String userId) {
        return null;
    }
    @Override
    public MedicalDataResponse getMedicalRecordForUserFiltered(int page, String userId, String category) {
        return null;
    }
}