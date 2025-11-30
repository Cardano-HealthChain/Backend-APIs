package com.cardano.healthchain.cardano.healthchain.utils.medicalData;

import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataResponse;
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
        return null;
    }
    @Override
    public ArrayList<MedicalDataResponse> getMedicalRecordsForUserFiltered(int page, String email, String category) {
        return null;
    }
    @Override
    public MedicalDataResponse getMedicalRecordById(String recordId) {
        return null;
    }
}