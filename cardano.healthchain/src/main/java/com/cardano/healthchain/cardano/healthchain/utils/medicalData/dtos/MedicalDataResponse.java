package com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class MedicalDataResponse {
    private String record_id;
    private String record_type;
    private String record_data;
    private String patientName;
    private String hash_local;
    private String blockchainTransactionID;
    private boolean verified = true;
    private String uploaded_by; // reflects clinic name in DB
    private LocalDate created_at;
}