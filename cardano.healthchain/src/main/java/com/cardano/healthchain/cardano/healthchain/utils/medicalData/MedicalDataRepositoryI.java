package com.cardano.healthchain.cardano.healthchain.utils.medicalData;

import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataResponse;

public interface MedicalDataRepositoryI {
    MedicalDataResponse getMedicalRecordForUser(int page, String userId);

    MedicalDataResponse getMedicalRecordForUserFiltered(int page, String userId, String category);
}