package com.cardano.healthchain.cardano.healthchain.utils;

import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataResponse;
import org.springframework.stereotype.Service;

@Service
public class MedicalRecordHashGeneratorService {
    public byte[] generateHashAndReturn(MedicalDataResponse medicalRecordForUser) {
        // create a hashing algorithm
        return "random".getBytes();
    }
}
