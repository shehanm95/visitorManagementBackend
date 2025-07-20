package com.tacniz.visitormanagement.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.ButtonAnswerDTO;
import com.tacniz.visitormanagement.mapper.ButtonAnswerMapper;
import com.tacniz.visitormanagement.model.ButtonAnswer;
import com.tacniz.visitormanagement.repo.ButtonAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ButtonAnswerMapperImpl implements ButtonAnswerMapper {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    @Lazy
    private ButtonAnswerRepository buttonAnswerRepository;

    @Override
    public ButtonAnswer toEntity(ButtonAnswerDTO dto) {
        if (dto == null) {
            return null;
        }
        System.out.println("converting button " + dto.getButtonText());
        if(dto.getId() != null){
            return buttonAnswerRepository.findById(dto.getId()).orElse(null);
        }else{
            return objectMapper.convertValue(dto, ButtonAnswer.class);
        }


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

    @Override
    public List<ButtonAnswerDTO> toDtoList(List<ButtonAnswer> buttonAnswers) {
        if (buttonAnswers == null) {
            return null;
        }
        return buttonAnswers.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ButtonAnswer> toEntityList(List<ButtonAnswerDTO> dtos) {
        if (dtos == null) {
            return null;
        }
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
