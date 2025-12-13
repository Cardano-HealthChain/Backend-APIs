package com.cardano.healthchain.cardano.healthchain.user.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class UserModel {
    @JsonIgnore
    private UUID user_id;
    private String email;
    @JsonIgnore
    private String hashed_password;
    private String first_name;
    private String last_name;
    private String phone_number;
    private LocalDate dob;
    private String gender;
    private String address;
    private String blood_type;
    private String genotype;
    private String known_allergies;
    private String pre_existing_conditions;
    private String emergency_contact_name;
    private String emergency_contact_phone;
    private String emergency_contact_rel;
    private String nationality;
    private String state_of_origin;
    private LocalDateTime created_at;
    private boolean verified;
    private String role;
    @JsonIgnore
    private String wallet_address;
    @JsonIgnore
    private String stake_address;
    @JsonIgnore
    private String public_key;
    @JsonIgnore
    private String wallet_verified_at;
    private String wallet_network;
    @JsonIgnore
    private String last_wallet_login;
    public UserModel() {
    }
    public UserModel(UUID user_id, String email, String hashed_password, String first_name, String last_name, String phone_number, LocalDate dob, String gender, String address, String blood_type, String genotype, String known_allergies, String pre_existing_conditions, String emergency_contact_name, String emergency_contact_phone, String emergency_contact_rel, String nationality, String state_of_origin, LocalDateTime created_at, boolean verified, String role, String wallet_address, String stake_address, String public_key, String wallet_verified_at, String wallet_network, String last_wallet_login) {
        this.user_id = user_id;
        this.email = email;
        this.hashed_password = hashed_password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.blood_type = blood_type;
        this.genotype = genotype;
        this.known_allergies = known_allergies;
        this.pre_existing_conditions = pre_existing_conditions;
        this.emergency_contact_name = emergency_contact_name;
        this.emergency_contact_phone = emergency_contact_phone;
        this.emergency_contact_rel = emergency_contact_rel;
        this.nationality = nationality;
        this.state_of_origin = state_of_origin;
        this.created_at = created_at;
        this.verified = verified;
        this.role = role;
        this.wallet_address = wallet_address;
        this.stake_address = stake_address;
        this.public_key = public_key;
        this.wallet_verified_at = wallet_verified_at;
        this.wallet_network = wallet_network;
        this.last_wallet_login = last_wallet_login;
    }
    public UUID getUser_id() {
        return user_id;
    }
    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getHashed_password() {
        return hashed_password;
    }
    public void setHashed_password(String hashed_password) {
        this.hashed_password = hashed_password;
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
    public String getPhone_number() {
        return phone_number;
    }
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
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
    public String getBlood_type() {
        return blood_type;
    }
    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }
    public String getGenotype() {
        return genotype;
    }
    public void setGenotype(String genotype) {
        this.genotype = genotype;
    }
    public String getKnown_allergies() {
        return known_allergies;
    }
    public void setKnown_allergies(String known_allergies) {
        this.known_allergies = known_allergies;
    }
    public String getPre_existing_conditions() {
        return pre_existing_conditions;
    }
    public void setPre_existing_conditions(String pre_existing_conditions) {
        this.pre_existing_conditions = pre_existing_conditions;
    }
    public String getEmergency_contact_name() {
        return emergency_contact_name;
    }
    public void setEmergency_contact_name(String emergency_contact_name) {
        this.emergency_contact_name = emergency_contact_name;
    }
    public String getEmergency_contact_phone() {
        return emergency_contact_phone;
    }
    public void setEmergency_contact_phone(String emergency_contact_phone) {
        this.emergency_contact_phone = emergency_contact_phone;
    }
    public String getEmergency_contact_rel() {
        return emergency_contact_rel;
    }
    public void setEmergency_contact_rel(String emergency_contact_rel) {
        this.emergency_contact_rel = emergency_contact_rel;
    }
    public String getNationality() {
        return nationality;
    }
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
    public String getState_of_origin() {
        return state_of_origin;
    }
    public void setState_of_origin(String state_of_origin) {
        this.state_of_origin = state_of_origin;
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
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getWallet_address() {
        return wallet_address;
    }
    public void setWallet_address(String wallet_address) {
        this.wallet_address = wallet_address;
    }
    public String getStake_address() {
        return stake_address;
    }
    public void setStake_address(String stake_address) {
        this.stake_address = stake_address;
    }
    public String getPublic_key() {
        return public_key;
    }
    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }
    public String getWallet_verified_at() {
        return wallet_verified_at;
    }
    public void setWallet_verified_at(String wallet_verified_at) {
        this.wallet_verified_at = wallet_verified_at;
    }
    public String getWallet_network() {
        return wallet_network;
    }
    public void setWallet_network(String wallet_network) {
        this.wallet_network = wallet_network;
    }
    public String getLast_wallet_login() {
        return last_wallet_login;
    }
    public void setLast_wallet_login(String last_wallet_login) {
        this.last_wallet_login = last_wallet_login;
    }
    @Override
    public String toString() {
        return "UserModel{" +
                "user_id=" + user_id +
                ", email='" + email + '\'' +
                ", hashed_password='" + hashed_password + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", dob=" + dob +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", blood_type='" + blood_type + '\'' +
                ", genotype='" + genotype + '\'' +
                ", known_allergies='" + known_allergies + '\'' +
                ", pre_existing_conditions='" + pre_existing_conditions + '\'' +
                ", emergency_contact_name='" + emergency_contact_name + '\'' +
                ", emergency_contact_phone='" + emergency_contact_phone + '\'' +
                ", emergency_contact_rel='" + emergency_contact_rel + '\'' +
                ", nationality='" + nationality + '\'' +
                ", state_of_origin='" + state_of_origin + '\'' +
                ", created_at=" + created_at +
                ", verified=" + verified +
                ", role='" + role + '\'' +
                ", wallet_address='" + wallet_address + '\'' +
                ", stake_address='" + stake_address + '\'' +
                ", public_key='" + public_key + '\'' +
                ", wallet_verified_at='" + wallet_verified_at + '\'' +
                ", wallet_network='" + wallet_network + '\'' +
                ", last_wallet_login='" + last_wallet_login + '\'' +
                '}';
    }
}