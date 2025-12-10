package com.cardano.healthchain.cardano.healthchain.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class UserCreateRequest {
    @NotBlank(message = "Email is Required")
    @Email(message = "Please provide a valid email")
    @Size(max = 100)
    private String email;

    @NotBlank(message = "Password is Required")
    @Size(min = 6, max = 100, message = "Password must be between 8 and 100 characters")
    private String password;
    @NotBlank(message = "First name is Required")
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    @Pattern(
            regexp = "^[a-zA-z\\s. '\\-]+$",
            message = "First name can only contain letters, spaces, apostrophes, dots and hyphens"
    )
    private String firstname;
    @NotBlank(message = "Last name is Required")
    @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters")
    @Pattern(
            regexp = "^[a-zA-z\\s. '\\-]+$",
            message = "Last name can only contain letters, spaces, apostrophes, dots and hyphens"
    )
    private String lastname;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}