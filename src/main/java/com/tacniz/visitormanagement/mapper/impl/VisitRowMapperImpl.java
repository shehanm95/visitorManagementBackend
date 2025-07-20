package com.tacniz.visitormanagement.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.VisitDto;
import com.tacniz.visitormanagement.dto.VisitRowDto;
import com.tacniz.visitormanagement.mapper.VisitRowMapper;
import com.tacniz.visitormanagement.model.VisitRow;
import com.tacniz.visitormanagement.repo.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VisitRowMapperImpl implements VisitRowMapper {

    private final VisitRepository visitRepository;
    private final ObjectMapper objectMapper;

    @Override
    public VisitRow toEntity(VisitRowDto visitRowDto) {
        return null;
    }

    @Override
    public VisitRowDto toDto(VisitRow visitRow) {
        if(visitRow == null) return null;
        VisitRowDto visitRowDto = objectMapper.convertValue(visitRow,VisitRowDto.class);
        visitRowDto.setVisits(visitRepository.findByVisitRowId(visitRow.getId())
                .stream()
                .map(v->objectMapper.convertValue(v, VisitDto.class))
                .toList());
        return visitRowDto;
    }
}
