package com.tacniz.visitormanagement.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.DynamicAnswerDto;
import com.tacniz.visitormanagement.model.DynamicAnswer;
import com.tacniz.visitormanagement.mapper.ButtonAnswerMapper;
import com.tacniz.visitormanagement.mapper.DynamicAnswerMapper;
import com.tacniz.visitormanagement.mapper.DynamicQuestionMapper;
import com.tacniz.visitormanagement.model.DynamicQuestion;
import com.tacniz.visitormanagement.model.Visit;
import com.tacniz.visitormanagement.repo.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DynamicAnswerMapperImpl implements DynamicAnswerMapper {

    private final ObjectMapper objectMapper;
    private final DynamicQuestionMapper dynamicQuestionMapper;
    private final ButtonAnswerMapper buttonAnswerMapper;

    @Autowired
    @Lazy
    private VisitRepository visitRepository;

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
        dto.setSelectedButtonAnswers(buttonAnswerMapper.toDtoList(answer.getSelectedButtonAnswers()));

        return dto;
    }

    public DynamicAnswer toEntity(DynamicAnswerDto dto) {
        if (dto == null) return null;

        DynamicAnswer answer = new DynamicAnswer();

        // Set basic fields
        answer.setId(dto.getId());
        answer.setAnswerType(dto.getAnswerType());
        answer.setValue(dto.getValue());

        // Set dynamic question
        if (dto.getDynamicQuestion() != null) {
            answer.setDynamicQuestion(dynamicQuestionMapper.toEntity(dto.getDynamicQuestion()));
        }

        // Set visit - just create a Visit object with ID, don't fetch from DB
        if (dto.getVisit() != null && dto.getVisit().getId() != null) {
            Visit visit = new Visit();
            visit.setId(dto.getVisit().getId());
            answer.setVisit(visit);
        }

        // Set selected button answers
        if (dto.getSelectedButtonAnswers() != null) {
            answer.setSelectedButtonAnswers(buttonAnswerMapper.toEntityList(dto.getSelectedButtonAnswers()));
        }

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
