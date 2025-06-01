package com.tacniz.visitormanagement.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DynamicQuestionDTO {

    private Long id;

    @NotNull(message = "visit option cannot be null")
    private VisitOptionDTO visitOption;


    @NotBlank(message = "Question text cannot be blank")
    @Size(max = 1000, message = "Question text must not exceed 1000 characters")
    private String questionText;

    @Size(max = 500, message = "Special instructions must not exceed 500 characters")
    private String specialInstructions;

    @NotNull(message = "isRequired cannot be null")
    private Boolean isRequired;

    @NotBlank(message = "Answer type cannot be blank")
    @Pattern(regexp = "button|number|text", message = "Answer type must be one of: button, number, text")
    private String answerType;

    @Size(min = 1, message = "A question needs to have at least 1 button answers")
    private List<ButtonAnswerDTO> buttonAnswers;

    @NotNull(message = "is Active question cannot be null")
    private boolean isActive;

    @NotNull(message = "Can select more than one answer cannot be null")
    private boolean canSelectMoreThanOne;
}