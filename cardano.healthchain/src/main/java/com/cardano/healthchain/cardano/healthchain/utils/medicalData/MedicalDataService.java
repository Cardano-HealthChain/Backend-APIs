package com.cardano.healthchain.cardano.healthchain.utils.medicalData;

import com.cardano.healthchain.cardano.healthchain.utils.MedicalRecordHashGeneratorService;
import com.cardano.healthchain.cardano.healthchain.utils.blockchain.BlockChainService;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataResponse;
import org.springframework.stereotype.Service;

@Service
public class MedicalDataService {
    private final MedicalDataRepositoryI medicalDataRepository;
    private final BlockChainService blockChainService;
    public MedicalDataService(MedicalDataRepositoryI medicalDataRepositoryI, BlockChainService blockChainService, MedicalRecordHashGeneratorService medicalRecordHashGeneratorService) {
        this.medicalDataRepository = medicalDataRepositoryI;
        this.blockChainService = blockChainService;
    }

    public MedicalDataResponse verifyAndGetMedicalRecordForUser(int page, String userId) {
        MedicalDataResponse medicalRecordForUser = medicalDataRepository.getMedicalRecordForUser(page, userId);
        blockChainService.verifyRecordHash(medicalRecordForUser);
        return medicalRecordForUser;
    }

    public MedicalDataResponse verifyAndGetMedicalRecordForUserFiltered(int page, String userId, String category) {
        MedicalDataResponse medicalRecordForUser = medicalDataRepository.getMedicalRecordForUserFiltered(page, userId, category);
        blockChainService.verifyRecordHash(medicalRecordForUser);
        return medicalRecordForUser;
    }
}
