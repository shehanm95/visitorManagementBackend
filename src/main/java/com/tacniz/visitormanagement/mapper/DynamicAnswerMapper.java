package com.tacniz.visitormanagement.mapper;

import com.tacniz.visitormanagement.dto.DynamicAnswerDto;
import com.tacniz.visitormanagement.model.DynamicAnswer;

import java.util.List;

public interface DynamicAnswerMapper {

    DynamicAnswerDto toDto(DynamicAnswer answer);

    DynamicAnswer toEntity(DynamicAnswerDto dto);

    List<DynamicAnswerDto> toDtoList(List<DynamicAnswer> answers);

    List<DynamicAnswer> toEntityList(List<DynamicAnswerDto> dtos);
}
