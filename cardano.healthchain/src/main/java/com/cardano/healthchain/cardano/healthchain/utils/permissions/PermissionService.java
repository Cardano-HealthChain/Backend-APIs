package com.cardano.healthchain.cardano.healthchain.utils.permissions;

import com.cardano.healthchain.cardano.healthchain.utils.audit.ACTOR_TYPE;
import com.cardano.healthchain.cardano.healthchain.utils.audit.AuditService;
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
    private final AuditService auditService;
    private final Logger logger = LoggerFactory.getLogger(PermissionService.class);
    public PermissionService(PermissionRepositoryI permissionRepository, MedicalDataService medicalDataService, AuditService auditService) {
        this.permissionRepository = permissionRepository;
        this.medicalDataService = medicalDataService;
        this.auditService = auditService;
    }
    @Transactional
    public ArrayList<PermissionResponse> getPermittedClinicsForUser(String user_email, int page) {
        auditService.logAuditEvent(ACTOR_TYPE.RESIDENT,user_email,"retrieved permitted clinics", "");
        return permissionRepository.getPermittedClinicsForUser(user_email, page);
    }

    public ArrayList<PermissionResponse> getRequestedPermissionsByClinic(String clinicId, int page) {
        return permissionRepository.getRequestedPermissionsByClinic(clinicId, page);
    }
    @Transactional
    public void permitClinic(String clinicId, String user_email, String permissionAccessScope) {
        auditService.logAuditEvent(ACTOR_TYPE.RESIDENT,user_email,"resident permitted a clinic", "");
        permissionRepository.permitClinic(clinicId, user_email, Instant.now().plus(1, ChronoUnit.DAYS), permissionAccessScope);
        medicalDataService.recordPermissionSharedWithClinic(user_email,clinicId);
    }
    public void revokeClinicPermissionForUser(String clinicId, String user_email) {
        auditService.logAuditEvent(ACTOR_TYPE.RESIDENT,user_email,"resident revoked a clinic permission", "");
        permissionRepository.revokeClinicPermissionForUser(clinicId, user_email);
    }
    public boolean deletePermissionRequestByClinic(String user_email, String clinicId) {
        permissionRepository.deletePermissionRequestByClinic(user_email,clinicId);
        return true;
    }
    @Transactional
    public void clinicRequestUserPermission(String clinic_email, String user_email, String accessType) {
        auditService.logAuditEvent(ACTOR_TYPE.CLINIC,clinic_email,"clinic requested user permission", "");
        permissionRepository.clinicRequestUserPermission(clinic_email, user_email, accessType);
    }

    public void checkClinicAccessToUserRecordStatus(String user_email) {

    }
    @Transactional
    public ArrayList<PermissionResponse> getClinicPermissionRequestsForUser(String user_email, int page) {
        auditService.logAuditEvent(ACTOR_TYPE.RESIDENT,user_email,"requested clinic permissions requests", "");
        return permissionRepository.getClinicPermissionRequests(user_email,page);
    }
}