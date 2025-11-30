package com.cardano.healthchain.cardano.healthchain.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@Builder
public class UserModel {
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate dob;
    private String gender;
    private String address;
    private String bloodType;
    private String genotype;
    private String knownAllergies;
    private String preExistingConditions;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String emergencyContactRelationship;
    private String nationality;
    private String stateOfOrigin;
    private LocalDateTime createdAt;
    private boolean verified;
    private String role;
}
