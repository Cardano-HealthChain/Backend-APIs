package com.cardano.healthchain.cardano.healthchain.clinics.dtos;

public class ClinicAdminCreateRequest {
    private String admin_name;
    private String admin_email_address;
    private String phone_number;
    private String password;
    private String clinic_email;
    public ClinicAdminCreateRequest() {
    }
    public ClinicAdminCreateRequest(String admin_name, String admin_email_address, String phone_number, String password, String clinic_email) {
        this.admin_name = admin_name;
        this.admin_email_address = admin_email_address;
        this.phone_number = phone_number;
        this.password = password;
        this.clinic_email = clinic_email;
    }
    public String getAdmin_name() {
        return admin_name;
    }
    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }
    public String getAdmin_email_address() {
        return admin_email_address;
    }
    public void setAdmin_email_address(String admin_email_address) {
        this.admin_email_address = admin_email_address;
    }
    public String getPhone_number() {
        return phone_number;
    }
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getClinic_email() {
        return clinic_email;
    }

    public void setClinic_email(String clinic_email) {
        this.clinic_email = clinic_email;
    }
}