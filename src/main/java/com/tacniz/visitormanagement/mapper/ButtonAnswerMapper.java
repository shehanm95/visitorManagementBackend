package com.tacniz.visitormanagement.mapper;

import com.tacniz.visitormanagement.dto.ButtonAnswerDTO;
import com.tacniz.visitormanagement.model.ButtonAnswer;

public interface ButtonAnswerMapper {
    ButtonAnswer toEntity(ButtonAnswerDTO dto);
    ButtonAnswerDTO toDto(ButtonAnswer entity);
}