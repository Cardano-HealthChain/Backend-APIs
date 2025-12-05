package com.cardano.healthchain.cardano.healthchain.utils.permissions.dtos;

import java.time.LocalDateTime;

public class PermissionStatusResponse {
    private String email;
    private String access_type;
    private String status;
    private LocalDateTime granted_at;

    public PermissionStatusResponse(String email, String access_type, String status, LocalDateTime granted_at) {
        this.email = email;
        this.access_type = access_type;
        this.status = status;
        this.granted_at = granted_at;
    }

    public PermissionStatusResponse() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccess_type() {
        return access_type;
    }

    public void setAccess_type(String access_type) {
        this.access_type = access_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getGranted_at() {
        return granted_at;
    }

    public void setGranted_at(LocalDateTime granted_at) {
        this.granted_at = granted_at;
    }
}
