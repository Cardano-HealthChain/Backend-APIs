package com.cardano.healthchain.cardano.healthchain.utils.medicalData;

import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataResponse;

import java.util.ArrayList;

public interface MedicalDataVerificationStrategyI {
    public ArrayList<MedicalDataResponse> verifyRecords(ArrayList<MedicalDataResponse> medicalDataResponseArrayList);
}