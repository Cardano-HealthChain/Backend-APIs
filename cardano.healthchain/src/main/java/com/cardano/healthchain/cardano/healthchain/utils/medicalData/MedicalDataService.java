package com.cardano.healthchain.cardano.healthchain.utils.medicalData;

import com.cardano.healthchain.cardano.healthchain.utils.MedicalRecordHashGeneratorService;
import com.cardano.healthchain.cardano.healthchain.utils.blockchain.BlockChainService;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MedicalDataService {
    private final MedicalDataRepositoryI medicalDataRepository;
    private final BlockChainService blockChainService;
    public MedicalDataService(MedicalDataRepositoryI medicalDataRepositoryI, BlockChainService blockChainService, MedicalRecordHashGeneratorService medicalRecordHashGeneratorService) {
        this.medicalDataRepository = medicalDataRepositoryI;
        this.blockChainService = blockChainService;
    }
    public MedicalDataResponse getMedicalRecordById(String record_id){
        return medicalDataRepository.getMedicalRecordById(record_id);
    }
    public ArrayList<MedicalDataResponse> verifyAndGetMedicalRecordsForUser(int page, String email) {
        ArrayList<MedicalDataResponse> medicalRecordsForUser = medicalDataRepository.getMedicalRecordsForUser(page, email);
        blockChainService.verifyMultipleRecordset(medicalRecordsForUser);
        return medicalRecordsForUser;
    }
    public ArrayList<MedicalDataResponse> verifyAndGetMedicalRecordsForUserFiltered(int page, String email, String category) {
        ArrayList<MedicalDataResponse> medicalRecordsForUser = medicalDataRepository.getMedicalRecordsForUserFiltered(page, email, category);
        blockChainService.verifyMultipleRecordset(medicalRecordsForUser);
        return medicalRecordsForUser;
    }
}