package com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.UUID;

public class MedicalDataResponse {
    private UUID user_id;
    private String record_id;
    private String record_type;
    private String record_data;
    @JsonIgnore
    private String hash_local;
    @JsonIgnore
    private String blockchainTransactionID;
    private boolean verified = true;
    private String doctor_uploaded; // reflects clinic name in DB
    private String clinic_uploaded; // reflects clinic name in DB
    private LocalDate created_at;

    public MedicalDataResponse() {
    }

    public MedicalDataResponse(String record_id, String record_type, String record_data, String hash_local, String blockchainTransactionID, boolean verified, String doctor_uploaded, String clinic_uploaded, LocalDate created_at) {
        this.record_id = record_id;
        this.record_type = record_type;
        this.record_data = record_data;
        this.hash_local = hash_local;
        this.blockchainTransactionID = blockchainTransactionID;
        this.verified = verified;
        this.doctor_uploaded = doctor_uploaded;
        this.clinic_uploaded = clinic_uploaded;
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

    public String getClinic_uploaded() {
        return clinic_uploaded;
    }

    public void setClinic_uploaded(String clinic_uploaded) {
        this.clinic_uploaded = clinic_uploaded;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }

    public String getDoctor_uploaded() {
        return doctor_uploaded;
    }

    public void setDoctor_uploaded(String doctor_uploaded) {
        this.doctor_uploaded = doctor_uploaded;
    }
}