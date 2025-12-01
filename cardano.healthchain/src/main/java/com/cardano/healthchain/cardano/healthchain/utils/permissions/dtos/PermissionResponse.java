package com.cardano.healthchain.cardano.healthchain.utils.permissions.dtos;

import com.cardano.healthchain.cardano.healthchain.utils.permissions.PermissionAccessScopes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class PermissionResponse {
    private String permissions_id;
    private String clinic_name;
    private String clinic_id;
    private PermissionAccessScopes access_type;
    private boolean granted;
    private boolean revoked;
    private LocalDateTime granted_at;
    private LocalDateTime revoked_at;
}
