package com.tacniz.visitormanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tacniz.visitormanagement.model.VisitType;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitOptionDTO {

    private Long id;

    @NotBlank(message = "Visit option name is required")
    @Size(min = 2, max = 100, message = "Visit option name must be between 2 and 100 characters")
    private String visitOptionName;

    @NotNull(message = "Visit type is required")
    private VisitTypeDTO visitType;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Pre-registration status is required")
    private Boolean isPreRegistration;

    private String imageName;

    @NotNull(message = "Photo requirement status is required")
    private Boolean isPhotoRequired;

    @NotNull(message = "Photo optional status is required")
    private Boolean isPhotoOptional;

    @NotNull(message = "Phone number requirement status is required")
    private Boolean isPhoneNumberRequired;

    @NotNull(message = "Email requirement status is required")
    private Boolean isEmailRequired;

    @JsonIgnore
    private MultipartFile image;

    private List<DynamicQuestionDTO> dynamicQuestions;
}