package com.cardano.healthchain.cardano.healthchain.utils.permissions;

import com.cardano.healthchain.cardano.healthchain.utils.permissions.dtos.PermissionResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@Service
public class PermissionService {
    private final PermissionRepositoryI permissionRepository;

    public PermissionService(PermissionRepositoryI permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public ArrayList<PermissionResponse> getPermittedClinicsForUser(String user_email, int page) {
        return permissionRepository.getPermittedClinicsForUser(user_email, page);
    }

    public ArrayList<PermissionResponse> getRequestedPermissionsByClinic(String clinicId, int page) {
        return permissionRepository.getRequestedPermissionsByClinic(clinicId, page);
    }

    public boolean permitClinic(String clinicId, String email) {
        permissionRepository.permitClinic(clinicId, email, Instant.now().plus(1, ChronoUnit.DAYS));
        return true;
    }
    public boolean revokeClinicPermissionForUser(String clinicId, String email) {
        permissionRepository.revokeClinicPermissionForUser(clinicId, email);
        return true;
    }
    public boolean deletePermissionRequestByClinic(String user_email, String clinicId) {
        permissionRepository.deletePermissionRequestByClinic(user_email,clinicId);
        return true;
    }
}
