package com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
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

    public MedicalDataResponse(String record_id, String record_type, String record_data, String patientName, String hash_local, String blockchainTransactionID, boolean verified, String uploaded_by, LocalDate created_at) {
        this.record_id = record_id;
        this.record_type = record_type;
        this.record_data = record_data;
        this.patientName = patientName;
        this.hash_local = hash_local;
        this.blockchainTransactionID = blockchainTransactionID;
        this.verified = verified;
        this.uploaded_by = uploaded_by;
        this.created_at = created_at;
    }

    public String getRecord_id() {
        return record_id;
    }

    public void setRecord_id(String record_id) {
        this.record_id = record_id;
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

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getHash_local() {
        return hash_local;
    }

    public void setHash_local(String hash_local) {
        this.hash_local = hash_local;
    }

    public String getBlockchainTransactionID() {
        return blockchainTransactionID;
    }

    public void setBlockchainTransactionID(String blockchainTransactionID) {
        this.blockchainTransactionID = blockchainTransactionID;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getUploaded_by() {
        return uploaded_by;
    }

    public void setUploaded_by(String uploaded_by) {
        this.uploaded_by = uploaded_by;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }
}