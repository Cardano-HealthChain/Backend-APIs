package com.cardano.healthchain.cardano.healthchain.utils.permissions.dtos;

import com.cardano.healthchain.cardano.healthchain.utils.permissions.PermissionAccessScopes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

public class PermissionResponse {
    private String permissions_id;
    private String clinic_name;
    private String clinic_id;
    private PermissionAccessScopes access_type;
    private boolean granted;
    private boolean revoked;
    private LocalDateTime granted_at;
    private LocalDateTime revoked_at;

    public PermissionResponse(String permissions_id, String clinic_name, String clinic_id, PermissionAccessScopes access_type, boolean granted, boolean revoked, LocalDateTime granted_at, LocalDateTime revoked_at) {
        this.permissions_id = permissions_id;
        this.clinic_name = clinic_name;
        this.clinic_id = clinic_id;
        this.access_type = access_type;
        this.granted = granted;
        this.revoked = revoked;
        this.granted_at = granted_at;
        this.revoked_at = revoked_at;
    }

    public PermissionResponse() {
    }

    public String getPermissions_id() {
        return permissions_id;
    }

    public void setPermissions_id(String permissions_id) {
        this.permissions_id = permissions_id;
    }

    public String getClinic_name() {
        return clinic_name;
    }

    public void setClinic_name(String clinic_name) {
        this.clinic_name = clinic_name;
    }

    public String getClinic_id() {
        return clinic_id;
    }

    public void setClinic_id(String clinic_id) {
        this.clinic_id = clinic_id;
    }

    public PermissionAccessScopes getAccess_type() {
        return access_type;
    }

    public void setAccess_type(PermissionAccessScopes access_type) {
        this.access_type = access_type;
    }

    public boolean isGranted() {
        return granted;
    }

    public void setGranted(boolean granted) {
        this.granted = granted;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public LocalDateTime getGranted_at() {
        return granted_at;
    }

    public void setGranted_at(LocalDateTime granted_at) {
        this.granted_at = granted_at;
    }

    public LocalDateTime getRevoked_at() {
        return revoked_at;
    }

    public void setRevoked_at(LocalDateTime revoked_at) {
        this.revoked_at = revoked_at;
    }
}