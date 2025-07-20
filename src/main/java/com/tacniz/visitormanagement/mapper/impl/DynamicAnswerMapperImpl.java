package com.tacniz.visitormanagement.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.DynamicAnswerDto;
import com.tacniz.visitormanagement.model.DynamicAnswer;
import com.tacniz.visitormanagement.mapper.ButtonAnswerMapper;
import com.tacniz.visitormanagement.mapper.DynamicAnswerMapper;
import com.tacniz.visitormanagement.mapper.DynamicQuestionMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DynamicAnswerMapperImpl implements DynamicAnswerMapper {

    private final ObjectMapper objectMapper;
    private final DynamicQuestionMapper dynamicQuestionMapper;
    private final ButtonAnswerMapper buttonAnswerMapper;

    public DynamicAnswerMapperImpl(ObjectMapper objectMapper,
                                   DynamicQuestionMapper dynamicQuestionMapper,
                                   ButtonAnswerMapper buttonAnswerMapper) {
        this.objectMapper = objectMapper;
        this.dynamicQuestionMapper = dynamicQuestionMapper;
        this.buttonAnswerMapper = buttonAnswerMapper;
    }

    @Override
    public DynamicAnswerDto toDto(DynamicAnswer answer) {
        if (answer == null) return null;

        DynamicAnswerDto dto = objectMapper.convertValue(answer, DynamicAnswerDto.class);
        dto.setDynamicQuestion(dynamicQuestionMapper.toDto(answer.getDynamicQuestion()));
        dto.setButtonAnswers(buttonAnswerMapper.toDtoList(answer.getButtonAnswers()));

        return dto;
    }

    @Override
    public DynamicAnswer toEntity(DynamicAnswerDto dto) {
        if (dto == null) return null;

        DynamicAnswer answer = objectMapper.convertValue(dto, DynamicAnswer.class);
        answer.setDynamicQuestion(dynamicQuestionMapper.toEntity(dto.getDynamicQuestion()));
        answer.setButtonAnswers(buttonAnswerMapper.toEntityList(dto.getButtonAnswers()));

        return answer;
    }

    @Override
    public List<DynamicAnswerDto> toDtoList(List<DynamicAnswer> answers) {
        if (answers == null) return null;
        return answers.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<DynamicAnswer> toEntityList(List<DynamicAnswerDto> dtos) {
        if (dtos == null) return null;
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
