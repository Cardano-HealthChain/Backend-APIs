package com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos;

import lombok.Builder;
import lombok.Data;

public class RecordDetails{
    private String record_id;
    private String record_hash;

    public RecordDetails(String record_id, String record_hash) {
        this.record_id = record_id;
        this.record_hash = record_hash;
    }

    public String getRecord_id() {
        return record_id;
    }

    public void setRecord_id(String record_id) {
        this.record_id = record_id;
    }

    public String getRecord_hash() {
        return record_hash;
    }

    public void setRecord_hash(String record_hash) {
        this.record_hash = record_hash;
    }
}