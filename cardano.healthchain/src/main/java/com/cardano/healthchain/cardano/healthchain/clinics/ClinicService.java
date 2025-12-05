package com.cardano.healthchain.cardano.healthchain.clinics;

import com.cardano.healthchain.cardano.healthchain.clinics.dtos.ClinicCreateRequest;
import com.cardano.healthchain.cardano.healthchain.clinics.dtos.ClinicCreateResponse;
import com.cardano.healthchain.cardano.healthchain.clinics.dtos.ClinicModel;
import com.cardano.healthchain.cardano.healthchain.utils.blockchain.BlockChainService;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataUploadRequest;
import com.cardano.healthchain.cardano.healthchain.utils.medicalData.dtos.MedicalDataUploadResponse;
import com.cardano.healthchain.cardano.healthchain.utils.notifications.NotificationService;
import com.cardano.healthchain.cardano.healthchain.utils.permissions.PermissionService;
import com.cardano.healthchain.cardano.healthchain.utils.permissions.dtos.PermissionStatusResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class ClinicService {
    private final ClinicRepositoryI clinicRepository;
    private final NotificationService notificationService;
    private final PermissionService permissionService;
    private final BlockChainService blockChainService;
    public ClinicService(ClinicRepositoryI clinicRepository, NotificationService notificationService, PermissionService permissionService, BlockChainService blockChainService) {
        this.clinicRepository = clinicRepository;
        this.notificationService = notificationService;
        this.permissionService = permissionService;
        this.blockChainService = blockChainService;
    }
    public int getTotalClinicsVisitedByUser(String user_email) {
        return clinicRepository.getTotalClinicsVisitedByUser(user_email);
    }

    public ClinicCreateResponse signUp(ClinicCreateRequest clinicCreateRequest) {
        return null;
    }

    public ClinicModel getClinicProfile(String name) {
        return null;
    }

    public void updateClinicRegion(String name, String newRegion) {
    }
    @Transactional
    public void requestRecordPermissionFromUser(String clinic_email, String user_email, String access_type) {
        permissionService.clinicRequestUserPermission(clinic_email, user_email,access_type);
        notificationService.insertClinicRequestNotification(
                user_email,
                "",
                "",
                "",
                ""
        );
    }

    public ArrayList<PermissionStatusResponse> viewPermissionStatusForClinic(String name) {
        return null;
    }

    public MedicalDataUploadResponse uploadMedicalDataForUser(MedicalDataUploadRequest medicalDataUploadRequest) {
        return null;
    }
}
