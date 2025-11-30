package com.cardano.healthchain.cardano.healthchain.utils.permissions;

import com.cardano.healthchain.cardano.healthchain.utils.permissions.dtos.PermissionResponse;

import java.time.Instant;
import java.util.ArrayList;

public interface PermissionRepositoryI {
    ArrayList<PermissionResponse> getPermittedClinicsForUser(String userId);

    PermissionResponse permitClinic(String clinicId, String userId, Instant Expires);

    void revokeClinicPermission(String clinicId);
}
