package com.tacniz.visitormanagement.dto;

import com.tacniz.visitormanagement.model.Duty;
import com.tacniz.visitormanagement.model.SpecialNote;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

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

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    private Boolean isPhoneNumberVerified; // Added to match schema

    private String role;

    private List<SpecialNoteDto> reviewedNotes;

    private List<DutyDto> duties = new ArrayList<>();
}