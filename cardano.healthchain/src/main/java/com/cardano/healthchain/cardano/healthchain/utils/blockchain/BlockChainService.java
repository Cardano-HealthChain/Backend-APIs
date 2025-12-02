package com.cardano.healthchain.cardano.healthchain.utils.blockchain;

import com.cardano.healthchain.cardano.healthchain.exceptions.RecordTamperedException;
import com.cardano.healthchain.cardano.healthchain.utils.MedicalRecordHashGeneratorService;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataResponse;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalRecordBatchRequest;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalRecordBatchResponse;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.RecordDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BlockChainService {
    private final MedicalRecordHashGeneratorService medicalRecordHashGeneratorService;
    public BlockChainService(MedicalRecordHashGeneratorService medicalRecordHashGeneratorService) {
        this.medicalRecordHashGeneratorService = medicalRecordHashGeneratorService;
    }
    public void verifyRecordHash(MedicalDataResponse medicalRecordForUser) throws NoSuchAlgorithmException, JsonProcessingException {
        //generate hash for medical record
        String medicalRecordHash = medicalRecordHashGeneratorService.generateHashAndReturn(medicalRecordForUser);
        //check if that hash exists on the blockchain if it doesn't throw custom RecordTamperedException if it does set verified to true and return
        if(!checkIfHashExistsOnCardanoBlockChain(medicalRecordHash)){
            medicalRecordForUser.setVerified(false);
            throw new RecordTamperedException(
                    medicalRecordForUser.getPatientName(),
                    medicalRecordForUser.getUploaded_by(),
                    medicalRecordForUser.getCreated_at().toString(),
                    medicalRecordForUser.getRecord_type()
            );
        };
    }
    public void verifyMultipleRecordset(ArrayList<MedicalDataResponse> medicalRecordsForUser) throws NoSuchAlgorithmException, JsonProcessingException {
        // Build RecordDetails list from medical records
        List<RecordDetails> recordDetails = medicalRecordsForUser.stream()
                .map(record -> {
                    try {
                        return RecordDetails.builder()
                                .record_hash(medicalRecordHashGeneratorService.generateHashAndReturn(record))
                                .record_id(record.getRecord_id())
                                .build();
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException("Hashing failed for record ID: " + record.getRecord_id(), e);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
        // Defensive check to avoid empty list issues
        if (medicalRecordsForUser.isEmpty()) {
            throw new IllegalArgumentException("No medical records found for user.");
        }
        // Create batch request for blockchain verification
        MedicalRecordBatchRequest batchedRecordMapping = new MedicalRecordBatchRequest(
                medicalRecordsForUser.get(0).getPatientName(),
                (ArrayList<RecordDetails>) recordDetails
        );
        // Send batch request to blockchain and get response
        MedicalRecordBatchResponse medicalRecordBatchResponse = this.checkIfHashesExistsOnCardanoBlockChain(batchedRecordMapping);
        // Retrieve failed records safely
        List<RecordDetails> failedRecords = medicalRecordBatchResponse.getFailed();
        // Create a map for fast lookup of original records by ID
        Map<String, MedicalDataResponse> recordMap = medicalRecordsForUser.stream()
                .collect(Collectors.toMap(MedicalDataResponse::getRecord_id, Function.identity()));
        // Update original records whose verification failed
        for (RecordDetails failedRecord : failedRecords) {
            MedicalDataResponse originalRecord = recordMap.get(failedRecord.getRecord_id());
            if (originalRecord != null) {
                originalRecord.setVerified(false);
            }
        }
    }
    public MedicalRecordBatchResponse checkIfHashesExistsOnCardanoBlockChain(MedicalRecordBatchRequest medicalRecordBatchRequest){
        //check if hash combination exists on redis if yes return, it doesn't exist move unto next step
        //Making the request
        return null;
    }
    public boolean checkIfHashExistsOnCardanoBlockChain(String medicalRecordHash){
        //check redis for cached hashes if has been verified
        //call endpoint for blockchain and check if hash exists return true or false depending on situation
        //insert hash into redis and write state
        return true;
    }
}