package com.cardano.healthchain.cardano.healthchain.clinics.dtos;

public class ClinicCreateResponse {
    private String role;
    private String jwt_token;

    public ClinicCreateResponse() {
    }

    public ClinicCreateResponse(String role, String jwt_token) {
        this.role = role;
        this.jwt_token = jwt_token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getJwt_token() {
        return jwt_token;
    }

    public void setJwt_token(String jwt_token) {
        this.jwt_token = jwt_token;
    }
}