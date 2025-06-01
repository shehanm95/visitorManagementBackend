package com.tacniz.visitormanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitorReqDto {
    private Long id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private String imagePath;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    private Boolean isEmailVerified;

    // Added to match schema
    @NotBlank(message = "Phone number is required")
    private String phoneNumber; // Added to match schema

    private Boolean isPhoneNumberVerified; // Added to match schema

    @NotBlank(message = "password is required")
    private String password;

    private String role; // Will map to the Role enum (visitor, officer, moderator)
}