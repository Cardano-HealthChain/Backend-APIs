package com.cardano.healthchain.cardano.healthchain.utils.blockchain;

import com.cardano.healthchain.cardano.healthchain.exceptions.RecordTamperedException;
import com.cardano.healthchain.cardano.healthchain.utils.MedicalRecordHashGeneratorService;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataResponse;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalRecordBatchRequest;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalRecordBatchResponse;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.RecordDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class BlockChainService {
    private final MedicalRecordHashGeneratorService medicalRecordHashGeneratorService;
    public BlockChainService(MedicalRecordHashGeneratorService medicalRecordHashGeneratorService) {
        this.medicalRecordHashGeneratorService = medicalRecordHashGeneratorService;
    }
    public void verifyRecordHash(MedicalDataResponse medicalRecordForUser) {
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
    public void verifyMultipleRecordset(ArrayList<MedicalDataResponse> medicalRecordsForUser){
        ArrayList<RecordDetails> recordDetails = new ArrayList<>();
        //Building record Details arraylist which combined hash and id to be sent to blockchain for verification
        for (MedicalDataResponse medicalRecord : medicalRecordsForUser){
            RecordDetails
                    .builder()
                    .record_hash(medicalRecordHashGeneratorService.generateHashAndReturn(medicalRecord))
                    .record_id(medicalRecord.getRecord_id())
                    .build();
        }
        //recordDetails is stored in http blockchain batch transfer object for batch verification
        MedicalRecordBatchRequest batchedRecordMapping = new MedicalRecordBatchRequest(medicalRecordsForUser.get(0).getPatientName(),recordDetails);
        //blockchain batch verifies medical records and returns a data structure with failed verifications and successes in different arraylists.
        MedicalRecordBatchResponse medicalRecordBatchResponse = this.checkIfHashesExistsOnCardanoBlockChain(batchedRecordMapping);
        //get records that failed verification
        ArrayList<RecordDetails> failedRecords = medicalRecordBatchResponse.getFailed();
        //retrieve original records sent for verification and get those whose IDs match with any failed verification and set verification to false
        //verification is naturally set to through all the time
        for(RecordDetails record : failedRecords){
            medicalRecordsForUser
                    .stream()
                    .filter(medicalDataResponse -> Objects.equals(medicalDataResponse.getRecord_id(), record.getRecord_id()))
                    .findFirst()
                    .ifPresent(medicalDataResponse -> medicalDataResponse.setVerified(false));
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
        return false;
    }
}