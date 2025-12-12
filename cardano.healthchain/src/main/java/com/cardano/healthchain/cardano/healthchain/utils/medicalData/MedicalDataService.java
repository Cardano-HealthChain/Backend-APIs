package com.cardano.healthchain.cardano.healthchain.utils.medicalData;

import com.cardano.healthchain.cardano.healthchain.utils.audit.ACTOR_TYPE;
import com.cardano.healthchain.cardano.healthchain.utils.audit.AuditService;
import com.cardano.healthchain.cardano.healthchain.utils.blockchain.BlockChainService;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

@Service
public class MedicalDataService {
    private final MedicalDataRepositoryI medicalDataRepository;
    private final AuditService auditService;
    private final BlockChainService blockChainService;
    private final Logger logger = LoggerFactory.getLogger(MedicalDataService.class);
    public MedicalDataService(MedicalDataRepositoryI medicalDataRepositoryI, AuditService auditService, BlockChainService blockChainService) {
        this.medicalDataRepository = medicalDataRepositoryI;
        this.auditService = auditService;
        this.blockChainService = blockChainService;
    }
    @Transactional
    public MedicalDataResponse getMedicalRecordById(String record_id, String user_email){
        MedicalDataResponse medicalRecordById = medicalDataRepository.getMedicalRecordById(record_id);
        auditService.logAuditEvent(ACTOR_TYPE.RESIDENT,user_email,"retrieved record", "Retrieved record by ID");
        logger.info("Medical records was successfully retrieved");
        return medicalRecordById;
    }
    @Transactional
    public ArrayList<MedicalDataResponse> verifyAndGetMedicalRecordsForUser(int page, String user_email) throws NoSuchAlgorithmException, JsonProcessingException {
        ArrayList<MedicalDataResponse> medicalRecordsForUser = medicalDataRepository.getMedicalRecordsForUser(page, user_email);
        auditService.logAuditEvent(ACTOR_TYPE.RESIDENT,user_email,"retrieved records", "Retrieved verified records for user");
//        blockChainService.verifyMultipleRecordset(medicalRecordsForUser);
        logger.info("Medical records for user was successfully verified on the blockchain and returned");
        return medicalRecordsForUser;
    }
    @Transactional
    public ArrayList<MedicalDataResponse> getMedicalRecordsForUser(int page, String user_email) throws NoSuchAlgorithmException, JsonProcessingException {
        auditService.logAuditEvent(ACTOR_TYPE.RESIDENT,user_email,"retrieved records", "Retrieved records for user");
        logger.info(String.format("Medical records for user with user_email: %s was successfully returned", user_email));
        return medicalDataRepository.getMedicalRecordsForUser(page,user_email);
    }
    @Transactional
    public ArrayList<MedicalDataResponse> getMedicalRecordsForUserFiltered(int page, String user_email, String category) throws NoSuchAlgorithmException, JsonProcessingException {
        auditService.logAuditEvent(ACTOR_TYPE.RESIDENT,user_email,"retrieved records", "Retrieved records for user");
        logger.info(String.format("Medical records for user with user_email: %s was successfully returned", user_email));
        return medicalDataRepository.getMedicalRecordsForUserFiltered(page,user_email,category);
    }
    public ArrayList<MedicalDataResponse> verifyAndGetMedicalRecordsForUserFiltered(int page, String user_email, String category) throws NoSuchAlgorithmException, JsonProcessingException {
        ArrayList<MedicalDataResponse> medicalRecordsForUser = medicalDataRepository.getMedicalRecordsForUserFiltered(page, user_email, category);
        auditService.logAuditEvent(ACTOR_TYPE.RESIDENT,user_email,"retrieved filtered records", "Retrieved filtered medical records for user");
        blockChainService.verifyMultipleRecordset(medicalRecordsForUser);
        logger.info(String.format("Medical records for user with user_email: %s was successfully verified on the blockchain and returned", user_email));
        return medicalRecordsForUser;
    }
    public int getVerifiedRecordsCountForUser(String user_id) {
        return medicalDataRepository.getVerifiedRecordCountForUser(user_id);
    }
    public void recordPermissionSharedWithClinic(String user_email, String clinicId) {
        medicalDataRepository.recordPermissionSharedWithClinic(user_email,clinicId);
    }
}