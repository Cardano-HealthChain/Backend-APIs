package com.cardano.healthchain.cardano.healthchain.utils.medicalData;

import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataResponse;

import java.util.ArrayList;

public interface MedicalDataRepositoryI {
    ArrayList<MedicalDataResponse> getMedicalRecordsForUser(int page, String email);

    ArrayList<MedicalDataResponse> getMedicalRecordsForUserFiltered(int page, String email, String category);

    MedicalDataResponse getMedicalRecordById(String recordId);
}