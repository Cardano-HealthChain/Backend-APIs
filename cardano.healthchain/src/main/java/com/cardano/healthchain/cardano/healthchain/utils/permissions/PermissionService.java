package com.cardano.healthchain.cardano.healthchain.utils.permissions;

import com.cardano.healthchain.cardano.healthchain.utils.permissions.dtos.PermissionResponse;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;

@Service
public class PermissionService {
    private final PermissionRepositoryI permissionRepository;
    public PermissionService(PermissionRepositoryI permissionRepository) {
        this.permissionRepository = permissionRepository;
    }
    public ArrayList<PermissionResponse> getPermittedClinicsForUser(String userId) {
        return permissionRepository.getPermittedClinicsForUser(userId);
    }

    public PermissionResponse permitClinic(String clinicId, String userId, String expiresIn) {
        return permissionRepository.permitClinic(clinicId, userId, parseExpiresOption(expiresIn));
    }
    public Instant parseExpiresOption(String expiresIn){
        return Instant.now().plus(200L, ChronoUnit.DAYS);
    }

    public boolean revokeClinicPermission(String clinicId) {
        permissionRepository.revokeClinicPermission(clinicId);
        return true;
    }
}
