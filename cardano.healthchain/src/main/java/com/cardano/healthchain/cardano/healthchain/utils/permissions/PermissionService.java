package com.cardano.healthchain.cardano.healthchain.utils.permissions;

import com.cardano.healthchain.cardano.healthchain.clinics.ClinicRepositoryI;
import com.cardano.healthchain.cardano.healthchain.clinics.doctors.DoctorRepositoryI;
import com.cardano.healthchain.cardano.healthchain.clinics.doctors.dtos.DoctorDataResponse;
import com.cardano.healthchain.cardano.healthchain.clinics.dtos.ClinicDataResponse;
import com.cardano.healthchain.cardano.healthchain.user.dtos.UserDataProfileResponse;
import com.cardano.healthchain.cardano.healthchain.utils.audit.AuditService;
import com.cardano.healthchain.cardano.healthchain.utils.permissions.dtos.PermissionDataResponse;
import com.cardano.healthchain.cardano.healthchain.utils.permissions.enums.PermissionAccessScopeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class PermissionService {
    private final PermissionRepositoryI permissionRepository;
    private final DoctorRepositoryI doctorRepository;
    private final ClinicRepositoryI clinicRepository;
    private final AuditService auditService;
    private final Logger logger = LoggerFactory.getLogger(PermissionService.class);
    public PermissionService(PermissionRepositoryI permissionRepository, DoctorRepositoryI doctorRepository, ClinicRepositoryI clinicRepository, AuditService auditService) {
        this.permissionRepository = permissionRepository;
        this.doctorRepository = doctorRepository;
        this.clinicRepository = clinicRepository;
        this.auditService = auditService;
    }
    public ArrayList<PermissionDataResponse> getPermittedClinicsForUserById(String userId, int page) {
        return permissionRepository.getPermittedClinicsForUserById(userId,page);
    }
    public ArrayList<PermissionDataResponse> getRequestedPermissionsForUserById(String userId, int page) {
        return permissionRepository.getRequestedPermissionsForUserById(userId,page);
    }

    public ArrayList<PermissionDataResponse> getClinicPermissionRequestsById(String clinicId, int page) {
        return permissionRepository.getClinicPermissionRequestsById(clinicId, page);
    }

    public ArrayList<UserDataProfileResponse> getUsersWhoPermittedClinicById(String clinicId, int page) {
        return permissionRepository.getUsersWhoPermittedClinicById(clinicId,page);
    }
    public void doctorRequestUserPermissionRead(String doctorId, String userId) {
        DoctorDataResponse doctorById = doctorRepository.getDoctorById(doctorId);
        ClinicDataResponse clinicById = clinicRepository.getClinicById(String.valueOf(doctorById.getClinic_id()));
        permissionRepository.doctorRequestUserPermission(doctorId,userId,PermissionAccessScopeEnum.READ.name(), doctorById, clinicById);
    }
    public void doctorRequestUserPermissionWrite(String doctorId, String userId) {
        DoctorDataResponse doctorById = doctorRepository.getDoctorById(doctorId);
        ClinicDataResponse clinicById = clinicRepository.getClinicById(String.valueOf(doctorById.getClinic_id()));
        permissionRepository.doctorRequestUserPermission(doctorId,userId,PermissionAccessScopeEnum.WRITE.name(), doctorById, clinicById);
    }
    public void doctorRequestUserPermissionReadAndWrite(String doctorId, String userId) {
        DoctorDataResponse doctorById = doctorRepository.getDoctorById(doctorId);
        ClinicDataResponse clinicById = clinicRepository.getClinicById(String.valueOf(doctorById.getClinic_id()));
        permissionRepository.doctorRequestUserPermission(doctorId,userId,PermissionAccessScopeEnum.READANDWRITE.name(), doctorById, clinicById);
    }
    public void userPermitClinic(String clinicId, String userId) {
        permissionRepository.userPermitClinic(clinicId,userId,LocalDateTime.now().plusDays(1));
    }
    public void userRevokeClinic(String clinicId, String userId) {
        permissionRepository.userRevokeClinic(clinicId,userId);
    }
    public void userDeleteClinicPermissionRequest(String userId, String clinicId) {
        permissionRepository.userDeleteClinicPermissionRequest(userId,clinicId);
    }
    public void clinicDeletePermissionRequest(String userId, String clinicId) {
        permissionRepository.clinicDeletePermissionRequest(userId,clinicId);
    }
    public void checkIfClinicHasWritePermissionForUser(String userId, String clinicId) {
        boolean hasWritePermissionForUser = permissionRepository.clinicHasWritePermissionForUser(userId, clinicId);
        if(!hasWritePermissionForUser){
            throw new RuntimeException("Clinic doesn't have write permission for user");
        }
    }
    public void checkIfClinicHasReadPermissionForUser(String userId, String clinicId) {
        boolean hasReadPermissionForUser = permissionRepository.clinicHasReadPermissionForUser(userId, clinicId);
        if(!hasReadPermissionForUser){
            throw new RuntimeException("Clinic doesn't have read permission for user");
        }
    }
    public void checkIfClinicHasReadAndWritePermissionForUser(String userId, String clinicId) {
        boolean hasReadAndWritePermissionForUser = permissionRepository.clinicHasReadAndWritePermissionForUser(userId, clinicId);
        if(!hasReadAndWritePermissionForUser){
            throw new RuntimeException("Clinic doesn't have read and write permission for user");
        }
    }

}