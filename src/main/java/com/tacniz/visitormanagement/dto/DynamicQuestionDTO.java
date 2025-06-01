package com.tacniz.visitormanagement.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DynamicQuestionDTO {

    private Integer id;

    @NotNull(message = "Visit option ID cannot be null")
    @Positive(message = "Visit option ID must be a positive number")
    private Integer visitOptionId;

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

    @DecimalMin(value = "0.0", inclusive = true, message = "Number answer must be greater than or equal to 0")
    private BigDecimal numberAnswer;

    @Size(max = 1000, message = "Text answer must not exceed 1000 characters")
    private String textAnswer;

    @Size(max = 10, message = "A question can have at most 10 button answers")
    private List<ButtonAnswerDTO> buttonAnswers;
}