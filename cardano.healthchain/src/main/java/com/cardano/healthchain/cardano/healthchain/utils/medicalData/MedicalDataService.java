package com.cardano.healthchain.cardano.healthchain.utils.medicalData;

import com.cardano.healthchain.cardano.healthchain.clinics.ClinicRepositoryI;
import com.cardano.healthchain.cardano.healthchain.clinics.doctors.DoctorRepositoryI;
import com.cardano.healthchain.cardano.healthchain.clinics.doctors.dtos.DoctorDataResponse;
import com.cardano.healthchain.cardano.healthchain.clinics.dtos.ClinicDataResponse;
import com.cardano.healthchain.cardano.healthchain.utils.audit.AuditService;
import com.cardano.healthchain.cardano.healthchain.utils.blockchain.BlockChainServiceI;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataResponse;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataUploadRequest;
import com.cardano.healthchain.cardano.healthchain.utils.notifications.NotificationService;
import com.cardano.healthchain.cardano.healthchain.utils.notifications.dtos.NotificationMessages;
import com.cardano.healthchain.cardano.healthchain.utils.notifications.dtos.NotificationSeverityLevel;
import com.cardano.healthchain.cardano.healthchain.utils.notifications.dtos.NotificationTypes;
import com.cardano.healthchain.cardano.healthchain.utils.permissions.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.ArrayList;

@Service
public class MedicalDataService {
    private final MedicalDataRepositoryI medicalDataRepository;
    private final MedicalDataVerificationStrategyI cardanoBlockChainVerificationStrategy;
    private final ClinicRepositoryI clinicRepository;
    private final DoctorRepositoryI doctorRepository;
    private final AuditService auditService;
    private final BlockChainServiceI cardanoBlockChainServiceImpl;
    private final NotificationService notificationService;
    private final PermissionService permissionService;
    private final Logger logger = LoggerFactory.getLogger(MedicalDataService.class);
    public MedicalDataService(MedicalDataRepositoryI medicalDataRepositoryI, MedicalDataVerificationStrategyI cardanoBlockChainVerificationStrategy, ClinicRepositoryI clinicRepository, DoctorRepositoryI doctorRepository, AuditService auditService, BlockChainServiceI cardanoBlockChainServiceImpl, NotificationService notificationService, PermissionService permissionService) {
        this.medicalDataRepository = medicalDataRepositoryI;
        this.cardanoBlockChainVerificationStrategy = cardanoBlockChainVerificationStrategy;
        this.clinicRepository = clinicRepository;
        this.doctorRepository = doctorRepository;
        this.auditService = auditService;
        this.cardanoBlockChainServiceImpl = cardanoBlockChainServiceImpl;
        this.notificationService = notificationService;
        this.permissionService = permissionService;
    }
    public MedicalDataResponse getMedicalRecordById(String recordId) {
        return medicalDataRepository.getMedicalRecordById(recordId);
    }
    public int getVerifiedRecordsCountForUser(String userId) {
        return medicalDataRepository.getVerifiedRecordCountForUser(userId);
    }
    public ArrayList<MedicalDataResponse> getMedicalRecordsForUser(String userId, int page) {
        return medicalDataRepository.getMedicalRecordsForUser(userId,page);
    }
    public ArrayList<MedicalDataResponse> clinicGetMedicalRecordsForUser(String userId, int page) {
        return medicalDataRepository.getMedicalRecordsForUser(userId,page);
    }
    public ArrayList<MedicalDataResponse> getMedicalRecordsForUserFiltered(String userId, String filterTerm, int page) {
        return medicalDataRepository.getMedicalRecordsForUserFiltered(userId,filterTerm,page);
    }
    public ArrayList<MedicalDataResponse> getVerifiedMedicalRecordsForUser(String userId, int page) {
        ArrayList<MedicalDataResponse> medicalRecordsForUser = medicalDataRepository.getMedicalRecordsForUser(userId, page);
//        return cardanoBlockChainVerificationStrategy.verifyRecords(medicalRecordsForUser);
//        WHEN IMPLEMENTED VERIFY RECORDS USING BLOCKCHAIN
        return medicalRecordsForUser;
    }
    public ArrayList<MedicalDataResponse> getVerifiedMedicalRecordsForUserFiltered(String userId, String filterTerm, int page) {
        ArrayList<MedicalDataResponse> medicalRecordsForUser = medicalDataRepository.getMedicalRecordsForUserFiltered(userId,filterTerm,page);
//        return cardanoBlockChainVerificationStrategy.verifyRecords(medicalRecordsForUser);
//        WHEN IMPLEMENTED VERIFY RECORDS USING BLOCKCHAIN
        return medicalRecordsForUser;
    }
    @Transactional
    public void uploadMedicalDataForUser(MedicalDataUploadRequest medicalDataUploadRequest, String doctorId, String userId) {
        DoctorDataResponse doctorById = doctorRepository.getDoctorById(doctorId);
        ClinicDataResponse clinicById = clinicRepository.getClinicById(String.valueOf(doctorById.getClinic_id()));
        permissionService.checkIfClinicHasWritePermissionForUser(userId,doctorById.getClinic_id().toString());
        notificationService.insertDoctorUploadedRecordForUser(userId,"RECORD_UPLOADED", NotificationMessages.DOCTOR_UPLOADED_RECORD.getMessage(), NotificationSeverityLevel.high.name(), NotificationTypes.healthUpdates.name());
        medicalDataRepository.uploadMedicalDataForUser(
                medicalDataUploadRequest,
                doctorId,
                doctorById.getClinic_id(),
                userId,
                generateRandomHashValue(),
                generateRandomHashValue(),
                generateRandomHashValue(),
                "1",
                doctorById.getFirst_name().concat(" ").concat(doctorById.getLast_name()),
                clinicById.getClinic_name());
    }
    private String generateRandomHashValue(){
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder randomHash = new StringBuilder();
        for(int i = 0; i < 10; i++){
            randomHash.append((char) secureRandom.nextInt());
        }
        return randomHash.toString();
    }
}