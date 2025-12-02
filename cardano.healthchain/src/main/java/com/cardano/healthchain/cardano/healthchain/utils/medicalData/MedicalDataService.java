package com.cardano.healthchain.cardano.healthchain.utils.medicalData;

import com.cardano.healthchain.cardano.healthchain.utils.blockchain.BlockChainService;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class MedicalDataService {
    private final MedicalDataRepositoryI medicalDataRepository;
    private final BlockChainService blockChainService;
    private final Logger logger = LoggerFactory.getLogger(MedicalDataService.class);
    public MedicalDataService(MedicalDataRepositoryI medicalDataRepositoryI, BlockChainService blockChainService) {
        this.medicalDataRepository = medicalDataRepositoryI;
        this.blockChainService = blockChainService;
    }
    public MedicalDataResponse getMedicalRecordById(String record_id){
        MedicalDataResponse medicalRecordById = medicalDataRepository.getMedicalRecordById(record_id);
        logger.info(String.format("Medical records for ID: %s was successfully retrieved",record_id));
        return medicalRecordById;
    }
    public ArrayList<MedicalDataResponse> verifyAndGetMedicalRecordsForUser(int page, String email) {
        ArrayList<MedicalDataResponse> medicalRecordsForUser = medicalDataRepository.getMedicalRecordsForUser(page, email);
        blockChainService.verifyMultipleRecordset(medicalRecordsForUser);
        logger.info(String.format("Medical records for user with email: %s was successfully verified on the blockchain and returned", email));
        return medicalRecordsForUser;
    }
    public ArrayList<MedicalDataResponse> verifyAndGetMedicalRecordsForUserFiltered(int page, String email, String category) {
        ArrayList<MedicalDataResponse> medicalRecordsForUser = medicalDataRepository.getMedicalRecordsForUserFiltered(page, email, category);
        blockChainService.verifyMultipleRecordset(medicalRecordsForUser);
        logger.info(String.format("Medical records for user with email: %s was successfully verified on the blockchain and returned", email));
        return medicalRecordsForUser;
    }
    public int getVerifiedRecordsCountForUser(String email) {
        return medicalDataRepository.getVerifiedRecordCountForUser(email);
    }
}