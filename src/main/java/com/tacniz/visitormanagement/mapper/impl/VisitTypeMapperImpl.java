package com.tacniz.visitormanagement.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.VisitTypeDTO;
import com.tacniz.visitormanagement.mapper.VisitTypeMapper;
import com.tacniz.visitormanagement.model.VisitType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitTypeMapperImpl implements VisitTypeMapper {
    private final ObjectMapper objectMapper;
    @Override
    public VisitTypeDTO toDto(VisitType visitType) {
        VisitTypeDTO visitTypeDTO = objectMapper.convertValue(visitType, VisitTypeDTO.class);
        return visitTypeDTO;
    }

    @Override
    public VisitType toEntity(VisitTypeDTO visitType) {
        return objectMapper.convertValue(visitType, VisitType.class);
    }

    @Override
    public List<VisitTypeDTO> toDtoList(List<VisitType> visitTypes) {
        return visitTypes.stream().map(this::toDto).toList();
    }
}
