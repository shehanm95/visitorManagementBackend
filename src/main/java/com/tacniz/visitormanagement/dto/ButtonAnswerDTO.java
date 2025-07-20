package com.tacniz.visitormanagement.dto;

import com.tacniz.visitormanagement.model.DynamicAnswer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ButtonAnswerDTO {

    private Long id;

    @NotBlank(message = "Option text cannot be blank")
    @Size(max = 255, message = "Option text must not exceed 255 characters")
    private String buttonText;

    private List<DynamicAnswerDto> dynamicAnswers = new ArrayList<>();
}