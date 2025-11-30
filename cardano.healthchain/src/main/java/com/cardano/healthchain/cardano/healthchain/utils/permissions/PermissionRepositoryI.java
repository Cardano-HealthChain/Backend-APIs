package com.cardano.healthchain.cardano.healthchain.utils.permissions;

import com.cardano.healthchain.cardano.healthchain.utils.permissions.dtos.PermissionResponse;

import java.time.Instant;
import java.util.ArrayList;

public interface PermissionRepositoryI {
    ArrayList<PermissionResponse> getPermittedClinicsForUser(String user_email, int page);
    ArrayList<PermissionResponse> getRequestedPermissionsByClinic(String clinicId, int page);
    void deletePermissionRequestByClinic(String user_email, String clinicId);
    void permitClinic(String clinicId, String userId, Instant Expires);

    void revokeClinicPermissionForUser(String clinicId, String email);
}
