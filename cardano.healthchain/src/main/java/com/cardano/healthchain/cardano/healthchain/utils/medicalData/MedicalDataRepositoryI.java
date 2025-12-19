package com.cardano.healthchain.cardano.healthchain.utils.medicalData;

import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataResponse;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataUploadRequest;

import java.util.ArrayList;
import java.util.UUID;

public interface MedicalDataRepositoryI {
    ArrayList<MedicalDataResponse> getMedicalRecordsForUser(String userId,int page);
    ArrayList<MedicalDataResponse> getMedicalRecordsForUserFiltered(String userId, String category,int page);
    MedicalDataResponse getMedicalRecordById(String recordId);
    int getVerifiedRecordCountForUser(String userId);
    void uploadMedicalDataForUser(MedicalDataUploadRequest medicalDataUploadRequest, String doctorId, UUID clinicId, String userId, String hashLocalValue, String blockchainTxId, String blockchainTxHash, String blockNumber, String doctorUploaded, String clinicUploaded);
    void recordPermissionSharedWithClinic(String userId, String clinicId);
}