package com.cardano.healthchain.cardano.healthchain.utils.permissions;

import com.cardano.healthchain.cardano.healthchain.utils.medicalData.MedicalDataService;
import com.cardano.healthchain.cardano.healthchain.utils.permissions.dtos.PermissionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@Service
public class PermissionService {
    private final PermissionRepositoryI permissionRepository;
    private final MedicalDataService medicalDataService;
    private final Logger logger = LoggerFactory.getLogger(PermissionService.class);
    public PermissionService(PermissionRepositoryI permissionRepository, MedicalDataService medicalDataService) {
        this.permissionRepository = permissionRepository;
        this.medicalDataService = medicalDataService;
    }
    public ArrayList<PermissionResponse> getPermittedClinicsForUser(String user_email, int page) {
        return permissionRepository.getPermittedClinicsForUser(user_email, page);
    }

    public ArrayList<PermissionResponse> getRequestedPermissionsByClinic(String clinicId, int page) {
        return permissionRepository.getRequestedPermissionsByClinic(clinicId, page);
    }

    @Transactional
    public void permitClinic(String clinicId, String email, String permissionAccessScope) {
        permissionRepository.permitClinic(clinicId, email, Instant.now().plus(1, ChronoUnit.DAYS), permissionAccessScope);
        medicalDataService.recordPermissionSharedWithClinic(email,clinicId);
    }
    public void revokeClinicPermissionForUser(String clinicId, String email) {
        permissionRepository.revokeClinicPermissionForUser(clinicId, email);
    }
    public boolean deletePermissionRequestByClinic(String user_email, String clinicId) {
        permissionRepository.deletePermissionRequestByClinic(user_email,clinicId);
        return true;
    }
}
