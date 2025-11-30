package com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos;

import lombok.Data;

import java.util.ArrayList;

@Data
public class MedicalRecordBatchResponse {
    private ArrayList<RecordDetails> verified;
    private ArrayList<RecordDetails> failed;
}