package com.cardano.healthchain.cardano.healthchain.clinics.dtos;

import java.time.LocalDate;

public class DoctorCreateRequest {
    private String doctor_email;
    private String doctor_password;
    private String doctor_firstname;
    private String doctor_lastname;
    private LocalDate dob;
    private String gender;
    private String address;
    private String phone_number;
    public DoctorCreateRequest() {
    }

    public DoctorCreateRequest(String doctor_email, String doctor_password, String doctor_firstname, String doctor_lastname, LocalDate dob, String gender, String address, String phone_number) {
        this.doctor_email = doctor_email;
        this.doctor_password = doctor_password;
        this.doctor_firstname = doctor_firstname;
        this.doctor_lastname = doctor_lastname;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.phone_number = phone_number;
    }

    public String getDoctor_email() {
        return doctor_email;
    }

    public void setDoctor_email(String doctor_email) {
        this.doctor_email = doctor_email;
    }

    public String getDoctor_password() {
        return doctor_password;
    }

    public void setDoctor_password(String doctor_password) {
        this.doctor_password = doctor_password;
    }

    public String getDoctor_firstname() {
        return doctor_firstname;
    }

    public void setDoctor_firstname(String doctor_firstname) {
        this.doctor_firstname = doctor_firstname;
    }

    public String getDoctor_lastname() {
        return doctor_lastname;
    }

    public void setDoctor_lastname(String doctor_lastname) {
        this.doctor_lastname = doctor_lastname;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    @Override
    public String toString() {
        return "DoctorCreateRequest{" +
                "doctor_email='" + doctor_email + '\'' +
                ", doctor_password='" + doctor_password + '\'' +
                ", doctor_firstname='" + doctor_firstname + '\'' +
                ", doctor_lastname='" + doctor_lastname + '\'' +
                ", dob=" + dob +
                ", gender='" + gender + '\'' +
                '}';
    }
}