package com.cardano.healthchain.cardano.healthchain.utils.permissions;

import com.cardano.healthchain.cardano.healthchain.clinics.doctors.dtos.DoctorDataResponse;
import com.cardano.healthchain.cardano.healthchain.clinics.dtos.ClinicDataResponse;
import com.cardano.healthchain.cardano.healthchain.user.dtos.UserDataProfileResponse;
import com.cardano.healthchain.cardano.healthchain.utils.permissions.dtos.PermissionDataResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface PermissionRepositoryI {
    ArrayList<PermissionDataResponse> getPermittedClinicsForUserById(String userId, int page);
    ArrayList<PermissionDataResponse> getRequestedPermissionsForUserById(String userId, int page);
    ArrayList<PermissionDataResponse> getClinicPermissionRequestsById(String clinicId, int page);
    ArrayList<UserDataProfileResponse> getUsersWhoPermittedClinicById(String clinicId, int page);
    void doctorRequestUserPermission(String doctorId, String userId, String PermissionAccessScopeEnum, DoctorDataResponse doctorRequestingPermission, ClinicDataResponse clinicDoctorIsRegisteredUnder);
    void userPermitClinic(String clinicId, String userId, LocalDateTime Expires);
    void userRevokeClinic(String clinicId, String userId);
    void userDeleteClinicPermissionRequest(String userId, String clinicId);
    void clinicDeletePermissionRequest(String userId, String clinicId);
    boolean clinicHasWritePermissionForUser(String userId, String clinicId);
    boolean clinicHasReadPermissionForUser(String userId, String clinicId);
    boolean clinicHasReadAndWritePermissionForUser(String userId, String clinicId);
}