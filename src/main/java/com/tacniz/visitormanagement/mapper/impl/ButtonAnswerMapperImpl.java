package com.tacniz.visitormanagement.mapper.impl;

import com.tacniz.visitormanagement.dto.ButtonAnswerDTO;
import com.tacniz.visitormanagement.mapper.ButtonAnswerMapper;
import com.tacniz.visitormanagement.model.ButtonAnswer;
import org.springframework.stereotype.Component;

@Component
public class ButtonAnswerMapperImpl implements ButtonAnswerMapper {

    @Override
    public ButtonAnswer toEntity(ButtonAnswerDTO dto) {
        if (dto == null) {
            return null;
        }
        ButtonAnswer entity = new ButtonAnswer();
        entity.setId(dto.getId());
        entity.setButtonText(dto.getOptionText());
        // Note: dynamicQuestion is not set here; it will be set by the parent (DynamicQuestionMapper)
        return entity;
    }

    @Override
    public ButtonAnswerDTO toDto(ButtonAnswer entity) {
        if (entity == null) {
            return null;
        }
        ButtonAnswerDTO dto = new ButtonAnswerDTO();
        dto.setId(entity.getId());
        dto.setOptionText(entity.getButtonText());
        return dto;
    }
}