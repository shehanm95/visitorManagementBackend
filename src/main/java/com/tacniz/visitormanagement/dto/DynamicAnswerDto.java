package com.tacniz.visitormanagement.dto;

import com.tacniz.visitormanagement.model.AnswerType;
import lombok.Data;

import java.util.List;

@Data
public class DynamicAnswerDto {
    private Long id;
    private DynamicQuestionDTO dynamicQuestion;
    private AnswerType answerType;
    private String value;
    private List<ButtonAnswerDTO> buttonAnswers;
}
