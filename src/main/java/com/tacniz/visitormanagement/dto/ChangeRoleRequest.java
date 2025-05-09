package com.tacniz.visitormanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangeRoleRequest {
    @NotBlank(message = "Id is required")
    private Long id;
    @NotBlank(message = "Role is required")
    private String role;
}
