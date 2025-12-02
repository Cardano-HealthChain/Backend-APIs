package com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
public class MedicalRecordBatchRequest {
    private String patient_name;
    private ArrayList<RecordDetails> records;

    public MedicalRecordBatchRequest(String patient_name, ArrayList<RecordDetails> records) {
        this.patient_name = patient_name;
        this.records = records;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public ArrayList<RecordDetails> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<RecordDetails> records) {
        this.records = records;
    }
}