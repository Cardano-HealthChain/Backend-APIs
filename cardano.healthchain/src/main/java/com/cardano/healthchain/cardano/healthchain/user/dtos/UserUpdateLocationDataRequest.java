package com.cardano.healthchain.cardano.healthchain.user.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public class UserUpdateLocationDataRequest {
    @NotBlank(message = "Country is required")
    private String country;
    @NotBlank(message = "State is required")
    private String state;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}