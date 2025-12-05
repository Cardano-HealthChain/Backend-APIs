package com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos;

public class MedicalDataUploadResponse {
    private String record_id;
    private String blockchain_tx_id;

    public MedicalDataUploadResponse(String record_id, String blockchain_tx_id) {
        this.record_id = record_id;
        this.blockchain_tx_id = blockchain_tx_id;
    }

    public MedicalDataUploadResponse() {
    }

    public String getRecord_id() {
        return record_id;
    }

    public void setRecord_id(String record_id) {
        this.record_id = record_id;
    }

    public String getBlockchain_tx_id() {
        return blockchain_tx_id;
    }

    public void setBlockchain_tx_id(String blockchain_tx_id) {
        this.blockchain_tx_id = blockchain_tx_id;
    }
}
