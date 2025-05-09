package com.tacniz.visitormanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "userEntity") // Updated to match the table name in the image
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 50) // Added length to match char(50)
    @NotBlank(message = "First name is required")
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50) // Added length to match char(50)
    @NotBlank(message = "Last name is required")
    private String lastName;


    private String imagePath;

    @Column(nullable = false, unique = true, length = 255) // Added length to match char(255)
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Column(name = "is_email_verified")
    private Boolean isEmailVerified; // Added to match schema

    @Column(name = "phone_number", nullable = false, length = 50) // Added phoneNumber field
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @Column(name = "is_phone_number_verified")
    private Boolean isPhoneNumberVerified; // Added to match schema

    @Column(nullable = false)
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private Role role; // Updated to match the schema field name

//    // Relationships based on foreign keys
//    @OneToMany(mappedBy = "visitor")
//    private List<Visit> visits; // FK1: visits

//    @ManyToOne
//    @JoinColumn(name = "service_points_as_moderator") // FK1: servicePoints (as moderator)
//    private ServicePointEntity servicePoints;

//    @ManyToOne
//    @JoinColumn(name = "service_point_as_officer") // FK2: servicePoint (as officer)
//    private ServicePointEntity servicePoint;

}