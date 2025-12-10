package com.cardano.healthchain.cardano.healthchain.user.dtos;

import java.time.LocalDate;

public class UserUpdateProfilePersonalDetails {
   private String firstname;
   private String lastname;
   private LocalDate dob;
   private String gender;

    public UserUpdateProfilePersonalDetails(String firstname, String lastname, LocalDate dob, String gender) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.dob = dob;
        this.gender = gender;
    }

    public UserUpdateProfilePersonalDetails() {
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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
}
