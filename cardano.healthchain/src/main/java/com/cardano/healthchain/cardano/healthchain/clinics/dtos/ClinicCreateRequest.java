package com.cardano.healthchain.cardano.healthchain.clinics.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ClinicCreateRequest {
    @NotBlank(message = "Clinic Name is Required")
    private String clinic_name;
    @NotBlank(message = "Email is Required")
    @Email(message = "Please provide a valid email")
    @Size(max = 100)
    private String clinic_email;
    @NotBlank(message = "Password is Required")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    private String clinic_password;
    @NotBlank(message = "Clinic Region is Required")
    private String region;
    public ClinicCreateRequest() {
    }
    public ClinicCreateRequest(String clinic_name, String clinic_email, String clinic_password, String region) {
        this.clinic_name = clinic_name;
        this.clinic_email = clinic_email;
        this.clinic_password = clinic_password;
        this.region = region;
    }
    public String getClinic_name() {
        return clinic_name;
    }
    public void setClinic_name(String clinic_name) {
        this.clinic_name = clinic_name;
    }
    public String getClinic_email() {
        return clinic_email;
    }
    public void setClinic_email(String clinic_email) {
        this.clinic_email = clinic_email;
    }
    public String getClinic_password() {
        return clinic_password;
    }
    public void setClinic_password(String clinic_password) {
        this.clinic_password = clinic_password;
    }
    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }
}