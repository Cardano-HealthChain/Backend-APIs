package com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
@Data
@AllArgsConstructor
public class MedicalRecordBatchRequest {
    private String patient_name;
    private ArrayList<RecordDetails> records;

}

