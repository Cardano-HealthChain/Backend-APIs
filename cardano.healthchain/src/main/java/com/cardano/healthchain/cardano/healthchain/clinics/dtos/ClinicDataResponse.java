package com.cardano.healthchain.cardano.healthchain.clinics.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public class ClinicDataResponse {
    private UUID clinic_id;
    private String clinic_name;
    private String clinic_facility_type;
    private String clinic_registration_number;
    private String clinic_email;
    private String clinic_phone_number;
    private String clinic_address;
    private String clinic_region;
    private String clinic_lga;
    private LocalDateTime created_at;
    private Boolean verified;

    // Clinic admin info
    private String clinic_admin_name;
    private String clinic_admin_email;
    private String clinic_admin_phone_number;
    private String clinic_admin_password;
    public ClinicDataResponse(UUID clinic_id, String clinic_name, String clinic_facility_type, String clinic_registration_number, String clinic_email, String clinic_phone_number, String clinic_address, String clinic_region, String clinic_lga, LocalDateTime created_at, Boolean verified, String clinic_admin_name, String clinic_admin_email, String clinic_admin_phone_number, String clinic_admin_password) {
        this.clinic_id = clinic_id;
        this.clinic_name = clinic_name;
        this.clinic_facility_type = clinic_facility_type;
        this.clinic_registration_number = clinic_registration_number;
        this.clinic_email = clinic_email;
        this.clinic_phone_number = clinic_phone_number;
        this.clinic_address = clinic_address;
        this.clinic_region = clinic_region;
        this.clinic_lga = clinic_lga;
        this.created_at = created_at;
        this.verified = verified;
        this.clinic_admin_name = clinic_admin_name;
        this.clinic_admin_email = clinic_admin_email;
        this.clinic_admin_phone_number = clinic_admin_phone_number;
        this.clinic_admin_password = clinic_admin_password;
    }
    public ClinicDataResponse() {
    }

    public UUID getClinic_id() {
        return clinic_id;
    }

    public void setClinic_id(UUID clinic_id) {
        this.clinic_id = clinic_id;
    }

    public String getClinic_name() {
        return clinic_name;
    }

    public void setClinic_name(String clinic_name) {
        this.clinic_name = clinic_name;
    }

    public String getClinic_facility_type() {
        return clinic_facility_type;
    }

    public void setClinic_facility_type(String clinic_facility_type) {
        this.clinic_facility_type = clinic_facility_type;
    }

    public String getClinic_registration_number() {
        return clinic_registration_number;
    }

    public void setClinic_registration_number(String clinic_registration_number) {
        this.clinic_registration_number = clinic_registration_number;
    }

    public String getClinic_email() {
        return clinic_email;
    }

    public void setClinic_email(String clinic_email) {
        this.clinic_email = clinic_email;
    }

    public String getClinic_phone_number() {
        return clinic_phone_number;
    }

    public void setClinic_phone_number(String clinic_phone_number) {
        this.clinic_phone_number = clinic_phone_number;
    }

    public String getClinic_address() {
        return clinic_address;
    }

    public void setClinic_address(String clinic_address) {
        this.clinic_address = clinic_address;
    }

    public String getClinic_region() {
        return clinic_region;
    }

    public void setClinic_region(String clinic_region) {
        this.clinic_region = clinic_region;
    }

    public String getClinic_lga() {
        return clinic_lga;
    }

    public void setClinic_lga(String clinic_lga) {
        this.clinic_lga = clinic_lga;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getClinic_admin_name() {
        return clinic_admin_name;
    }

    public void setClinic_admin_name(String clinic_admin_name) {
        this.clinic_admin_name = clinic_admin_name;
    }

    public String getClinic_admin_email() {
        return clinic_admin_email;
    }

    public void setClinic_admin_email(String clinic_admin_email) {
        this.clinic_admin_email = clinic_admin_email;
    }

    public String getClinic_admin_phone_number() {
        return clinic_admin_phone_number;
    }

    public void setClinic_admin_phone_number(String clinic_admin_phone_number) {
        this.clinic_admin_phone_number = clinic_admin_phone_number;
    }

    public String getClinic_admin_password() {
        return clinic_admin_password;
    }

    public void setClinic_admin_password(String clinic_admin_password) {
        this.clinic_admin_password = clinic_admin_password;
    }

    @Override
    public String toString() {
        return "ClinicDataResponse{" +
                "clinic_id=" + clinic_id +
                ", clinic_name='" + clinic_name + '\'' +
                ", clinic_facility_type='" + clinic_facility_type + '\'' +
                ", clinic_registration_number='" + clinic_registration_number + '\'' +
                ", clinic_email='" + clinic_email + '\'' +
                ", clinic_phone_number='" + clinic_phone_number + '\'' +
                ", clinic_address='" + clinic_address + '\'' +
                ", clinic_region='" + clinic_region + '\'' +
                ", clinic_lga='" + clinic_lga + '\'' +
                ", created_at=" + created_at +
                ", verified=" + verified +
                ", clinic_admin_name='" + clinic_admin_name + '\'' +
                ", clinic_admin_email='" + clinic_admin_email + '\'' +
                ", clinic_admin_phone_number='" + clinic_admin_phone_number + '\'' +
                ", clinic_admin_password='" + clinic_admin_password + '\'' +
                '}';
    }
}