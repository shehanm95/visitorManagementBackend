package com.tacniz.visitormanagement.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.VisitTypeDTO;
import com.tacniz.visitormanagement.mapper.VisitTypeMapper;
import com.tacniz.visitormanagement.model.VisitType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        return null;
    }
}
