package com.tacniz.visitormanagement.mapper;

import com.tacniz.visitormanagement.dto.VisitDto;
import com.tacniz.visitormanagement.model.Visit;

import java.util.List;

public interface VisitMapper {

    VisitDto toDto(Visit visit);

    Visit toEntity(VisitDto dto);

    List<VisitDto> toDtoList(List<Visit> visits);

    List<Visit> toEntityList(List<VisitDto> dtos);
}
