package com.cardano.healthchain.cardano.healthchain.user.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class UserUpdateProfilePersonalDetails {
    @NotBlank(message = "First name is required")
    private String first_name;
    @NotBlank(message = "Last name is required")
    private String last_name;
    @NotBlank(message = "Date of Birth is required")
    private LocalDate dob;
    @NotBlank(message = "Gender is required")
    private String gender;
}
