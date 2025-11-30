package com.cardano.healthchain.cardano.healthchain.user.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdateProfileHealthInformation {
    @NotBlank(message = "Blood type is required")
    public String blood_type;
    @NotBlank(message = "Genotype is required")
    private String genotype;
    private String known_allergies;
    private String pre_existing_conditions;
}
