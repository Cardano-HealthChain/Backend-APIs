package com.cardano.healthchain.cardano.healthchain.clinics.doctors.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public class DoctorDataResponse {
    private UUID doctor_id;
    private UUID clinic_id;
    private String doctor_email;
    @JsonIgnore
    private String password;
    private String first_name;
    private String last_name;
    private String gender;
    private String address;
    public DoctorDataResponse() {
    }
    public DoctorDataResponse(UUID doctor_id, UUID clinic_id, String doctor_email, String password, String first_name, String last_name, String gender, String address) {
        this.doctor_id = doctor_id;
        this.clinic_id = clinic_id;
        this.doctor_email = doctor_email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.address = address;
    }

    public UUID getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(UUID doctor_id) {
        this.doctor_id = doctor_id;
    }

    public UUID getClinic_id() {
        return clinic_id;
    }

    public void setClinic_id(UUID clinic_id) {
        this.clinic_id = clinic_id;
    }

    public String getDoctor_email() {
        return doctor_email;
    }

    public void setDoctor_email(String doctor_email) {
        this.doctor_email = doctor_email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
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

    @Override
    public String toString() {
        return "DoctorDataResponse{" +
                "doctor_id=" + doctor_id +
                ", clinic_id=" + clinic_id +
                ", doctor_email='" + doctor_email + '\'' +
                ", password='" + password + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}