package com.cardano.healthchain.cardano.healthchain.user.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
public class UserUpdateProfileHealthInformation {
    @NotBlank(message = "Blood type is required")
    public String blood_type;
    @NotBlank(message = "Genotype is required")
    private String genotype;
    private String known_allergies;
    private String pre_existing_conditions;

    public String getBlood_type() {
        return blood_type;
    }

    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }

    public String getGenotype() {
        return genotype;
    }

    public void setGenotype(String genotype) {
        this.genotype = genotype;
    }

    public String getKnown_allergies() {
        return known_allergies;
    }

    public void setKnown_allergies(String known_allergies) {
        this.known_allergies = known_allergies;
    }

    public String getPre_existing_conditions() {
        return pre_existing_conditions;
    }

    public void setPre_existing_conditions(String pre_existing_conditions) {
        this.pre_existing_conditions = pre_existing_conditions;
    }
}