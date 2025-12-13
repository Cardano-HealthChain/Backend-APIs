package com.cardano.healthchain.cardano.healthchain.utils.permissions;

import com.cardano.healthchain.cardano.healthchain.utils.permissions.dtos.PermissionResponse;

import java.time.Instant;
import java.util.ArrayList;

public interface PermissionRepositoryI {
    ArrayList<PermissionResponse> getPermittedClinicsForUser(String user_id, int page);
    ArrayList<PermissionResponse> getRequestedPermissionsByClinic(String clinicId, int page);
    void deletePermissionRequestByClinic(String user_id, String clinicId);
    void permitClinic(String clinicId, String user_id, Instant Expires, String permissionAccessScopes);

    void revokeClinicPermissionForUser(String clinicId, String user_id);

    void clinicRequestUserPermission(String clinicEmail, String user_id, String accessType);

    ArrayList<PermissionResponse> getClinicPermissionRequests(String user_id, int page);
}
