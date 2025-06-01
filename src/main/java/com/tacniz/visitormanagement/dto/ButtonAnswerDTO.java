package com.tacniz.visitormanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ButtonAnswerDTO {

    private Integer id;

    @NotBlank(message = "Option text cannot be blank")
    @Size(max = 255, message = "Option text must not exceed 255 characters")
    private String buttonText;
}