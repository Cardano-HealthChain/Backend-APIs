package com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos;

import jakarta.validation.constraints.NotBlank;

public class MedicalDataUploadRequest {
    @NotBlank(message = "Record type is required")
    private String record_type;
    @NotBlank(message = "Record data is required")
    private String record_data;
    private String diagnosis;
    public MedicalDataUploadRequest(String record_type, String record_data, String diagnosis) {
        this.record_type = record_type;
        this.record_data = record_data;
        this.diagnosis = diagnosis;
    }
    public MedicalDataUploadRequest() {
    }
    public String getRecord_type() {
        return record_type;
    }
    public void setRecord_type(String record_type) {
        this.record_type = record_type;
    }
    public String getRecord_data() {
        return record_data;
    }
    public void setRecord_data(String record_data) {
        this.record_data = record_data;
    }
    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
}