package com.cardano.healthchain.cardano.healthchain.clinics.dtos;

import java.time.LocalDateTime;

public class ClinicModel {
    private String clinic_name;
    private String region;
    private String address;
    private String user_email;
    private LocalDateTime created_at;
    private boolean verified;
    private String license_no;
    public ClinicModel() {
    }
    public ClinicModel(String clinic_name, String region, String address, String user_email, LocalDateTime created_at, boolean verified, String license_no) {
        this.clinic_name = clinic_name;
        this.region = region;
        this.address = address;
        this.user_email = user_email;
        this.created_at = created_at;
        this.verified = verified;
        this.license_no = license_no;
    }

    public String getClinic_name() {
        return clinic_name;
    }

    public void setClinic_name(String clinic_name) {
        this.clinic_name = clinic_name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getLicense_no() {
        return license_no;
    }

    public void setLicense_no(String license_no) {
        this.license_no = license_no;
    }

    @Override
    public String toString() {
        return "ClinicModel{" +
                "clinic_name='" + clinic_name + '\'' +
                ", region='" + region + '\'' +
                ", address='" + address + '\'' +
                ", user_email='" + user_email + '\'' +
                ", created_at=" + created_at +
                ", verified=" + verified +
                ", license_no='" + license_no + '\'' +
                '}';
    }
}