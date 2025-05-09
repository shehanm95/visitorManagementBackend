package com.tacniz.visitormanagement.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.VisitOptionDTO;
import com.tacniz.visitormanagement.dto.VisitTypeDTO;
import com.tacniz.visitormanagement.mapper.VisitOptionMapper;
import com.tacniz.visitormanagement.model.VisitOption;
import com.tacniz.visitormanagement.model.VisitType;
import com.tacniz.visitormanagement.repo.VisitTypeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VisitOptionMapperImpl implements VisitOptionMapper {
    private final ObjectMapper objectMapper;
    private final VisitTypeRepo visitTypeRepo;
    @Override
    public VisitOption toEntity (VisitOptionDTO visitOptionDTO){
        if(visitOptionDTO == null) return null;
        VisitOption visitOption =   objectMapper.convertValue(visitOptionDTO, VisitOption.class);
        visitOption.setVisitType(objectMapper.convertValue(visitOptionDTO.getVisitType(), VisitType.class));
        return visitOption;
    }

    @Override
    public VisitOptionDTO toDto(VisitOption visitOptionEntity) {
        if (visitOptionEntity == null) return null;
        VisitOptionDTO visitOptionDTO = objectMapper.convertValue(visitOptionEntity, VisitOptionDTO.class);
        VisitType visitType = visitTypeRepo.findById(visitOptionDTO.getVisitType().getId()).orElseThrow( ()->new IllegalArgumentException("visit type not found"));
        visitOptionDTO.setVisitType(objectMapper.convertValue(visitType, VisitTypeDTO.class));
        return visitOptionDTO;
    }
}
