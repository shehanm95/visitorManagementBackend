package com.tacniz.visitormanagement.mapper;

import com.tacniz.visitormanagement.dto.ButtonAnswerDTO;
import com.tacniz.visitormanagement.model.ButtonAnswer;

import java.util.List;

public interface ButtonAnswerMapper {
    ButtonAnswer toEntity(ButtonAnswerDTO dto);
    ButtonAnswerDTO toDto(ButtonAnswer entity);

    List<ButtonAnswerDTO> toDtoList(List<ButtonAnswer> buttonAnswers);
    List<ButtonAnswer> toEntityList(List<ButtonAnswerDTO> dtos);

}