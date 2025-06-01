package com.tacniz.visitormanagement.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.ButtonAnswerDTO;
import com.tacniz.visitormanagement.mapper.ButtonAnswerMapper;
import com.tacniz.visitormanagement.model.ButtonAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ButtonAnswerMapperImpl implements ButtonAnswerMapper {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public ButtonAnswer toEntity(ButtonAnswerDTO dto) {
        if (dto == null) {
            return null;
        }
        // Use ObjectMapper to map basic fields
        ButtonAnswer entity = objectMapper.convertValue(dto, ButtonAnswer.class);
        // Note: dynamicQuestion is not set here; it will be set by the parent (DynamicQuestionMapper)
        return entity;
    }

    @Override
    public ButtonAnswerDTO toDto(ButtonAnswer entity) {
        if (entity == null) {
            return null;
        }
        // Use ObjectMapper to map basic fields
        ButtonAnswerDTO dto = objectMapper.convertValue(entity, ButtonAnswerDTO.class);
        return dto;
    }
}