package com.tacniz.visitormanagement.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.IdVisitOptionObject;
import com.tacniz.visitormanagement.dto.VisitDto;
import com.tacniz.visitormanagement.model.Visit;
import com.tacniz.visitormanagement.mapper.*;
import com.tacniz.visitormanagement.model.VisitOption;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VisitMapperImpl implements VisitMapper {

    private final ObjectMapper objectMapper;
    private final UserMapper userMapper;
    private final DynamicAnswerMapper dynamicAnswerMapper;
    private final VisitOptionMapper visitOptionMapper;
    private final VisitRowMapper visitRowMapper;
    private final GateMapper gateMapper;

    public VisitMapperImpl(ObjectMapper objectMapper,
                           UserMapper userMapper,
                           DynamicAnswerMapper dynamicAnswerMapper,
                           VisitOptionMapper visitOptionMapper,
                           VisitRowMapper visitRowMapper,
                           GateMapper gateMapper) {
        this.objectMapper = objectMapper;
        this.userMapper = userMapper;
        this.dynamicAnswerMapper = dynamicAnswerMapper;
        this.visitOptionMapper = visitOptionMapper;
        this.visitRowMapper = visitRowMapper;
        this.gateMapper = gateMapper;
    }

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
}
