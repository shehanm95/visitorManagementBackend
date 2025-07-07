package com.tacniz.visitormanagement.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.IdObject;
import com.tacniz.visitormanagement.dto.TimeRangeDto;
import com.tacniz.visitormanagement.dto.VisitRowDto;
import com.tacniz.visitormanagement.model.TimeRange;
import com.tacniz.visitormanagement.model.VisitOption;
import com.tacniz.visitormanagement.repo.VisitOptionRepository;
import com.tacniz.visitormanagement.repo.VisitRowRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TimeRangeMapper {

    private final ObjectMapper objectMapper;
    private final VisitRowRepo visitRowRepository;
    private final VisitOptionRepository visitOptionRepository;




    public TimeRangeDto toDto(TimeRange entity) {
        TimeRangeDto dto = objectMapper.convertValue(entity, TimeRangeDto.class);

        // Set IdObject for visitOption
        if (entity.getVisitOption() != null) {
            dto.setVisitOption(new IdObject(entity.getVisitOption().getId()));
        }

        // Convert VisitRows if needed
        if (entity.getVisitRows() != null && !entity.getVisitRows().isEmpty()) {
            List<VisitRowDto> visitRowDtos = entity.getVisitRows().stream()
                    .map(visitRow -> {
                        VisitRowDto visitRowDto = new VisitRowDto();
                        visitRowDto.setId(visitRow.getId());
                        // Set other fields as needed
                        return visitRowDto;
                    })
                    .collect(Collectors.toList());
            dto.setVisitRows(visitRowDtos);
        }

        return dto;
    }

    public TimeRange toEntity(TimeRangeDto dto) {
        TimeRange entity = objectMapper.convertValue(dto, TimeRange.class);

        // Set VisitOption from IdObject
        if (dto.getVisitOption() != null && dto.getVisitOption().getId() != null) {
            VisitOption visitOption = visitOptionRepository.findById(dto.getVisitOption().getId())
                    .orElseThrow(() -> new EntityNotFoundException("VisitOption not found"));
            entity.setVisitOption(visitOption);
        }

        // You might want to handle VisitRows here if needed
        // Note: This might need more complex handling depending on your use case

        return entity;
    }

    public List<TimeRangeDto> toDtoList(List<TimeRange> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}