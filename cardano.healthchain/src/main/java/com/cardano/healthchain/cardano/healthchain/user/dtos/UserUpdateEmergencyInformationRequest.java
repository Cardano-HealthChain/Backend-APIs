package com.cardano.healthchain.cardano.healthchain.user.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public class UserUpdateEmergencyInformationRequest {
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "phone number is required")
    private String phone_number;
    @NotBlank(message = "relationship is required")
    private String relationship;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
}