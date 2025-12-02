package com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos;

import lombok.Data;

import java.util.ArrayList;

public class MedicalRecordBatchResponse {
    private ArrayList<RecordDetails> verified;
    private ArrayList<RecordDetails> failed;

    public ArrayList<RecordDetails> getVerified() {
        return verified;
    }

    public void setVerified(ArrayList<RecordDetails> verified) {
        this.verified = verified;
    }

    public ArrayList<RecordDetails> getFailed() {
        return failed;
    }

    public void setFailed(ArrayList<RecordDetails> failed) {
        this.failed = failed;
    }
}