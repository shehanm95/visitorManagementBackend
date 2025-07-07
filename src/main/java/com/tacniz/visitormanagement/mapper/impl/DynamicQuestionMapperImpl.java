package com.tacniz.visitormanagement.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.ButtonAnswerDTO;
import com.tacniz.visitormanagement.dto.DynamicQuestionDTO;
import com.tacniz.visitormanagement.mapper.ButtonAnswerMapper;
import com.tacniz.visitormanagement.mapper.DynamicQuestionMapper;
import com.tacniz.visitormanagement.mapper.VisitOptionMapper;
import com.tacniz.visitormanagement.model.AnswerType;
import com.tacniz.visitormanagement.model.ButtonAnswer;
import com.tacniz.visitormanagement.model.DynamicQuestion;
import com.tacniz.visitormanagement.model.VisitOption;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DynamicQuestionMapperImpl implements DynamicQuestionMapper {
    @Autowired
    private  ButtonAnswerMapper buttonAnswerMapper;
   @Autowired
    private  ObjectMapper objectMapper;

    @Override
    public DynamicQuestion toEntity(DynamicQuestionDTO dto) {
        if (dto == null) {
            return null;
        }

        DynamicQuestion dynamicQuestion = objectMapper.convertValue(dto, DynamicQuestion.class);
        dynamicQuestion.setVisitOption(objectMapper.convertValue(dto.getVisitOption(), VisitOption.class));
        dynamicQuestion.setAnswerType(dto.getAnswerType() != null ? AnswerType.valueOf(dto.getAnswerType()) : null);

        // Map buttonAnswers and set the bidirectional relationship
        if (dto.getButtonAnswers() != null) {
            List<ButtonAnswer> buttonAnswers = dto.getButtonAnswers().stream()
                    .map(buttonAnswerMapper::toEntity)
                    .peek(buttonAnswer -> buttonAnswer.setDynamicQuestion(dynamicQuestion))
                    .collect(Collectors.toList());
            dynamicQuestion.setButtonAnswers(buttonAnswers);
        } else {
            dynamicQuestion.setButtonAnswers(Collections.emptyList());
        }

        return dynamicQuestion;
    }

    @Override
    public DynamicQuestionDTO toDto(DynamicQuestion entity) {
        if (entity == null) {
            return null;
        }

        // Use ObjectMapper to map basic fields
        DynamicQuestionDTO dto = objectMapper.convertValue(entity, DynamicQuestionDTO.class);
        dto.setAnswerType(entity.getAnswerType() != null ? entity.getAnswerType().name() : null);

        // Map buttonAnswers
        if (entity.getButtonAnswers() != null) {
            List<ButtonAnswerDTO> buttonAnswerDTOs = entity.getButtonAnswers().stream()
                    .map(buttonAnswerMapper::toDto)
                    .collect(Collectors.toList());
            dto.setButtonAnswers(buttonAnswerDTOs);
        } else {
            dto.setButtonAnswers(Collections.emptyList());
        }

        return dto;
    }
}