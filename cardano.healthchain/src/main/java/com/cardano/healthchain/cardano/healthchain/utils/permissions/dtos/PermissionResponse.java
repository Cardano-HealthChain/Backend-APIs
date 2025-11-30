package com.cardano.healthchain.cardano.healthchain.utils.permissions.dtos;

import com.cardano.healthchain.cardano.healthchain.utils.permissions.PermissionAccessScopes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PermissionResponse {
    public String clinicId;
    public PermissionAccessScopes permissionAccessScopes;
}
