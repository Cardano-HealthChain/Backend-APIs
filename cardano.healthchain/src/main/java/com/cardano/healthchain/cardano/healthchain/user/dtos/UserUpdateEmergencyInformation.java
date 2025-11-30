package com.cardano.healthchain.cardano.healthchain.user.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateEmergencyInformation {
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "phone number is required")
    private String phone_number;
    @NotBlank(message = "relationship is required")
    private String relationship;
}
