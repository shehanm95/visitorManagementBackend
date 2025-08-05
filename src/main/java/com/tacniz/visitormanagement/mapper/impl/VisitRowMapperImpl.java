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
        if (visitRowDto == null) {
            return null;
        }

        VisitRow visitRow = new VisitRow();
        visitRow.setId(visitRowDto.getId());
        visitRow.setDate(visitRowDto.getDate());
        visitRow.setStartTime(visitRowDto.getStartTime());
        visitRow.setEndTime(visitRowDto.getEndTime());
        visitRow.setAverageTimeForAPerson(visitRowDto.getAverageTimeForAPerson());
        visitRow.setVisitorsPerRow(visitRowDto.getVisitorsPerRow());

        // Note: timeRange and visitOption are not set here as they're @JsonIgnore in the entity
        // They should be handled separately in the service layer if needed

        return visitRow;
    }

    @Override
    public VisitRowDto toDto(VisitRow visitRow) {
        if (visitRow == null) {
            return null;
        }

        VisitRowDto visitRowDto = new VisitRowDto();
        visitRowDto.setId(visitRow.getId());
        visitRowDto.setDate(visitRow.getDate());
        visitRowDto.setStartTime(visitRow.getStartTime());
        visitRowDto.setEndTime(visitRow.getEndTime());
        visitRowDto.setAverageTimeForAPerson(visitRow.getAverageTimeForAPerson());
        visitRowDto.setVisitorsPerRow(visitRow.getVisitorsPerRow());

        // Map visits using the existing logic
        visitRowDto.setVisits(visitRepository.findByVisitRowId(visitRow.getId())
                .stream()
                .map(v -> objectMapper.convertValue(v, VisitDto.class))
                .toList());

        // Note: timeRange and visitOption are not set here as they're @JsonIgnore in the entity
        // They should be handled separately if needed

        return visitRowDto;
    }
}