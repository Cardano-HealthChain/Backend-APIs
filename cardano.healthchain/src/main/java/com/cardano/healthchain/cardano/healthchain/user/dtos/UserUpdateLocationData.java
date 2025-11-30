package com.cardano.healthchain.cardano.healthchain.user.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateLocationData {
    @NotBlank(message = "Country is required")
    private String country;
    @NotBlank(message = "State is required")
    private String state;
}