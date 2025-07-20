package com.tacniz.visitormanagement.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.GateDto;
import com.tacniz.visitormanagement.model.Gate;
import com.tacniz.visitormanagement.mapper.GateMapper;
import com.tacniz.visitormanagement.mapper.VisitMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GateMapperImpl implements GateMapper {

    private final ObjectMapper objectMapper;
    private final VisitMapper visitMapper;

    public GateMapperImpl(ObjectMapper objectMapper, VisitMapper visitMapper) {
        this.objectMapper = objectMapper;
        this.visitMapper = visitMapper;
    }

    @Override
    public GateDto toDto(Gate gate) {
        if (gate == null) return null;

        GateDto dto = objectMapper.convertValue(gate, GateDto.class);
        dto.setEnteredVisits(visitMapper.toDtoList(gate.getEnteredVisits()));
        dto.setExitGate(visitMapper.toDtoList(gate.getExitGate()));

        return dto;
    }

    @Override
    public Gate toEntity(GateDto dto) {
        if (dto == null) return null;

        Gate gate = objectMapper.convertValue(dto, Gate.class);
        gate.setEnteredVisits(visitMapper.toEntityList(dto.getEnteredVisits()));
        gate.setExitGate(visitMapper.toEntityList(dto.getExitGate()));

        return gate;
    }

    @Override
    public List<GateDto> toDtoList(List<Gate> gates) {
        if (gates == null) return null;
        return gates.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<Gate> toEntityList(List<GateDto> dtos) {
        if (dtos == null) return null;
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
