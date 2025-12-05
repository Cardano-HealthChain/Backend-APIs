package com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos;

import jakarta.validation.constraints.NotBlank;

public class MedicalDataUploadRequest {
    @NotBlank(message = "User email is required")
    private String user_email;
    @NotBlank(message = "Record type is required")
    private String record_type;
    @NotBlank(message = "Record data is required")
    private String record_data;

    public MedicalDataUploadRequest(String user_email, String record_type, String record_data) {
        this.user_email = user_email;
        this.record_type = record_type;
        this.record_data = record_data;
    }

    public MedicalDataUploadRequest() {
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
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
}
