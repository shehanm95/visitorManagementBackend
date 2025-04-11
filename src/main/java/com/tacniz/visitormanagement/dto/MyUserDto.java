package com.tacniz.visitormanagement.dto;

import com.tacniz.visitormanagement.model.MyUser.UserRole;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyUserDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private UserRole userRole;
}