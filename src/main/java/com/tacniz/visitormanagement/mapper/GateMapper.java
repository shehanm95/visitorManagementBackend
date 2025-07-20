package com.tacniz.visitormanagement.mapper;

import com.tacniz.visitormanagement.dto.GateDto;
import com.tacniz.visitormanagement.model.Gate;

import java.util.List;

public interface GateMapper {

    GateDto toDto(Gate gate);

    Gate toEntity(GateDto dto);

    List<GateDto> toDtoList(List<Gate> gates);

    List<Gate> toEntityList(List<GateDto> dtos);
}
