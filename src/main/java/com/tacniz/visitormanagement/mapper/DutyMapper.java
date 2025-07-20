package com.tacniz.visitormanagement.mapper;

import com.tacniz.visitormanagement.dto.DutyDto;
import com.tacniz.visitormanagement.model.Duty;

import java.util.List;

public interface DutyMapper {

    DutyDto toDto(Duty duty);

    Duty toEntity(DutyDto dutyDto);

    List<DutyDto> toDtoList(List<Duty> duties);

    List<Duty> toEntityList(List<DutyDto> dutyDtos);
}
