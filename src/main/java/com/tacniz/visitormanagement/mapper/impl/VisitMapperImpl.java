package com.tacniz.visitormanagement.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.*;
import com.tacniz.visitormanagement.model.Visit;
import com.tacniz.visitormanagement.mapper.*;
import com.tacniz.visitormanagement.model.VisitOption;
import com.tacniz.visitormanagement.repo.VisitOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VisitMapperImpl implements VisitMapper {

    @Autowired
    @Lazy
    private ObjectMapper objectMapper;
    @Autowired
    @Lazy
    private UserMapper userMapper;
    @Autowired
    @Lazy
    private DynamicAnswerMapper dynamicAnswerMapper;
    @Autowired
    @Lazy
    private VisitOptionMapper visitOptionMapper;
    @Autowired
    @Lazy
    private VisitRowMapper visitRowMapper;
    @Autowired
    @Lazy
    private GateMapper gateMapper;
    @Autowired
    @Lazy
    private VisitOptionRepository visitOptionRepository;


    @Override
    public VisitDto toDto(Visit visit) {
        if (visit == null) return null;

        VisitDto dto = objectMapper.convertValue(visit, VisitDto.class);

        dto.setVisitOption(objectMapper.convertValue(visit.getVisitOption(), IdVisitOptionObject.class));
        dto.setVisitor(userMapper.toDto(visit.getVisitor()));
        dto.setDynamicAnswers(dynamicAnswerMapper.toDtoList(visit.getDynamicAnswers()));
        dto.setVisitRow(visitRowMapper.toDto(visit.getVisitRow()));
        dto.setEnteredGate(gateMapper.toDto(visit.getEnteredGate()));
        dto.setExitGate(gateMapper.toDto(visit.getExitGate()));

        return dto;
    }

    @Override
    public Visit toEntity(VisitDto dto) {
        if (dto == null) return null;

        Visit visit = objectMapper.convertValue(dto, Visit.class);

        visit.setVisitOption(objectMapper.convertValue(dto.getVisitOption(), VisitOption.class));
        visit.setVisitor(userMapper.toEntity(dto.getVisitor()));

        visit.setDynamicAnswers(dynamicAnswerMapper.toEntityList(dto.getDynamicAnswers()));
        visit.getDynamicAnswers().forEach(a->a.setVisit(visit));

        visit.setVisitRow(visitRowMapper.toEntity(dto.getVisitRow()));
        visit.setEnteredGate(gateMapper.toEntity(dto.getEnteredGate()));
        visit.setExitGate(gateMapper.toEntity(dto.getExitGate()));

        return visit;
    }

    @Override
    public List<VisitDto> toDtoList(List<Visit> visits) {
        if (visits == null) return null;
        return visits.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<Visit> toEntityList(List<VisitDto> dtos) {
        if (dtos == null) return null;
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }


    @Override
    public FullVisitDto toFullVisitDto(Visit visit){

        FullVisitDto fullVisitDto = objectMapper.convertValue( this.toDto(visit),FullVisitDto.class);
        VisitOption visitOption = visitOptionRepository.findById(visit.getVisitOption().getId()).orElse(null);

        VisitOptionDTO visitOptionDTO = visitOptionMapper.toDto(visitOption);
        fullVisitDto.setVisitOption(visitOptionDTO);
        fullVisitDto.getVisitRow().setVisits(Collections.emptyList());
        return fullVisitDto;
    }


}
