package com.tacniz.visitormanagement.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.ButtonAnswerDTO;
import com.tacniz.visitormanagement.dto.DynamicQuestionDTO;
import com.tacniz.visitormanagement.mapper.ButtonAnswerMapper;
import com.tacniz.visitormanagement.mapper.DynamicQuestionMapper;
import com.tacniz.visitormanagement.model.AnswerType;
import com.tacniz.visitormanagement.model.ButtonAnswer;
import com.tacniz.visitormanagement.model.DynamicQuestion;
import com.tacniz.visitormanagement.model.VisitOption;
import com.tacniz.visitormanagement.repo.DynamicQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private DynamicQuestionRepository dynamicQuestionRepository;

    @Override
    public DynamicQuestion toEntity(DynamicQuestionDTO dto) {
        if (dto == null) {
            return null;
        }

        DynamicQuestion dynamicQuestion = objectMapper.convertValue(dto, DynamicQuestion.class);
        dynamicQuestion.setVisitOption(objectMapper.convertValue(dto.getVisitOption(), VisitOption.class));


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

        if (dto.getReferenceQuestions() != null) {
            List<DynamicQuestion> references = dto.getReferenceQuestions().stream()
                    .map(refDto -> dynamicQuestionRepository.findById(refDto.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Reference question not found: " + refDto.getId())))
                    .collect(Collectors.toList());

            for (DynamicQuestion reference : references) {
                reference.addParentQuestion(dynamicQuestion); // Sets both sides
            }

            dynamicQuestion.setReferenceQuestions(references);
        } else {
            dynamicQuestion.setReferenceQuestions(Collections.emptyList());
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

        List<DynamicQuestionDTO> references = dynamicQuestionRepository.findReferenceQuestionsByParentId(entity.getId())
                .stream()
                .map(rq->objectMapper.convertValue(rq,DynamicQuestionDTO.class))
                .collect(Collectors.toList());

        dto.setReferenceQuestions(references);
        return dto;
    }

    @Override
    public List<DynamicQuestionDTO> toDtoList(List<DynamicQuestion> officerQuestions) {
        return officerQuestions.stream().map(this::toDto).toList();
    }

    @Override
    public void updateEntityFromDto(DynamicQuestionDTO updatedQuestion, DynamicQuestion existing) {

    }
}