package com.tacniz.visitormanagement.mapper.impl.;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.ButtonAnswerDTO;
import com.tacniz.visitormanagement.dto.DynamicQuestionDTO;
import com.tacniz.visitormanagement.mapper.ButtonAnswerMapper;
import com.tacniz.visitormanagement.mapper.DynamicQuestionMapper;
import com.tacniz.visitormanagement.model.AnswerType;
import com.tacniz.visitormanagement.model.ButtonAnswer;
import com.tacniz.visitormanagement.model.DynamicQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DynamicQuestionMapperImpl implements DynamicQuestionMapper {

    @Autowired
    private ButtonAnswerMapper buttonAnswerMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public DynamicQuestion toEntity(DynamicQuestionDTO dto) {
        if (dto == null) {
            return null;
        }

        // Use ObjectMapper to map basic fields
        DynamicQuestion entity = objectMapper.convertValue(dto, DynamicQuestion.class);
        entity.setAnswerType(dto.getAnswerType() != null ? AnswerType.valueOf(dto.getAnswerType()) : null);

        // Map buttonAnswers and set the bidirectional relationship
        if (dto.getButtonAnswers() != null) {
            List<ButtonAnswer> buttonAnswers = dto.getButtonAnswers().stream()
                    .map(buttonAnswerMapper::toEntity)
                    .peek(buttonAnswer -> buttonAnswer.setDynamicQuestion(entity))
                    .collect(Collectors.toList());
            entity.setButtonAnswers(buttonAnswers);
        } else {
            entity.setButtonAnswers(Collections.emptyList());
        }

        return entity;
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