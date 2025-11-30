package com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicalDataResponse {
    private String record_type;
    private String record_data;
    private String patientName;
    private String blockchainTransactionID;
}