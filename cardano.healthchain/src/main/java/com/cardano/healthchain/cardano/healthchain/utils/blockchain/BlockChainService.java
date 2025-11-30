package com.cardano.healthchain.cardano.healthchain.utils.blockchain;

import com.cardano.healthchain.cardano.healthchain.exceptions.RecordTamperedException;
import com.cardano.healthchain.cardano.healthchain.utils.MedicalRecordHashGeneratorService;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataResponse;
import org.springframework.stereotype.Service;

@Service
public class BlockChainService {
    private final MedicalRecordHashGeneratorService medicalRecordHashGeneratorService;
    public BlockChainService(MedicalRecordHashGeneratorService medicalRecordHashGeneratorService) {
        this.medicalRecordHashGeneratorService = medicalRecordHashGeneratorService;
    }
    public void verifyRecordHash(MedicalDataResponse medicalRecordForUser) {
        byte[] medicalRecordHash = medicalRecordHashGeneratorService.generateHashAndReturn(medicalRecordForUser);
        if(!checkIfHashExistsOnCardanoBlockChain(medicalRecordHash)){
            medicalRecordForUser.ge
            throw new RecordTamperedException("","",medicalRecordForUser., medicalRecordForUser.getRecord_type());
        };
    }
    public boolean checkIfHashExistsOnCardanoBlockChain(byte[] medicalRecordHash){
        //check redis for cached hashes
        //call endpoint for blockchain and check if hash exists return true or false depending on situation
        return false;
    }
}
