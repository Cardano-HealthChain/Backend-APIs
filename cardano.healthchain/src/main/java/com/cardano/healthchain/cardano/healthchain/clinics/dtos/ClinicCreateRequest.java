package com.cardano.healthchain.cardano.healthchain.clinics.dtos;

public class ClinicCreateRequest {
    private String clinic_name;
    private String facility_type;
    private String registration_number;
    private String clinic_email;
    private String clinic_phone_number;
    private String clinic_password;
    private String clinic_address;
    private String region;
    private String lga;
    public ClinicCreateRequest() {
    }

    public ClinicCreateRequest(String clinic_name, String facility_type, String registration_number, String clinic_email, String clinic_phone_number, String clinic_password, String clinic_address, String region, String lga) {
        this.clinic_name = clinic_name;
        this.facility_type = facility_type;
        this.registration_number = registration_number;
        this.clinic_email = clinic_email;
        this.clinic_phone_number = clinic_phone_number;
        this.clinic_password = clinic_password;
        this.clinic_address = clinic_address;
        this.region = region;
        this.lga = lga;
    }
    public String getClinic_name() {
        return clinic_name;
    }

    public void setClinic_name(String clinic_name) {
        this.clinic_name = clinic_name;
    }

    public String getFacility_type() {
        return facility_type;
    }

    public void setFacility_type(String facility_type) {
        this.facility_type = facility_type;
    }

    public String getRegistration_number() {
        return registration_number;
    }

    public void setRegistration_number(String registration_number) {
        this.registration_number = registration_number;
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

    public String getClinic_password() {
        return clinic_password;
    }

    public void setClinic_password(String clinic_password) {
        this.clinic_password = clinic_password;
    }

    public String getClinic_address() {
        return clinic_address;
    }

    public void setClinic_address(String clinic_address) {
        this.clinic_address = clinic_address;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLga() {
        return lga;
    }

    public void setLga(String lga) {
        this.lga = lga;
    }
    @Override
    public String toString() {
        return "ClinicCreateRequest{" +
                "clinic_name='" + clinic_name + '\'' +
                ", facility_type='" + facility_type + '\'' +
                ", registration_number='" + registration_number + '\'' +
                ", clinic_email='" + clinic_email + '\'' +
                ", clinic_phone_number='" + clinic_phone_number + '\'' +
                ", clinic_password='" + clinic_password + '\'' +
                ", clinic_address='" + clinic_address + '\'' +
                ", region='" + region + '\'' +
                ", lga='" + lga + '\'' +
                '}';
    }
}