package com.cardano.healthchain.cardano.healthchain.utils.permissions.dtos;

import com.cardano.healthchain.cardano.healthchain.utils.permissions.enums.PermissionAccessScopeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.UUID;

public class PermissionDataResponse {
    private UUID permissions_id;
    @JsonIgnore
    private UUID clinic_id;
    @JsonIgnore
    private UUID doctor_id;
    private String clinic_name;
    private String doctor_name;
    private PermissionAccessScopeEnum access_type;
    private boolean granted;
    private boolean revoked;
    private LocalDateTime granted_at;
    private LocalDateTime revoked_at;
}