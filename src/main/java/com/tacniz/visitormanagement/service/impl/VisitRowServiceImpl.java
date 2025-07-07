package com.tacniz.visitormanagement.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.VisitRowDto;
import com.tacniz.visitormanagement.mapper.VisitRowMapper;
import com.tacniz.visitormanagement.model.VisitRow;
import com.tacniz.visitormanagement.repo.VisitRowRepo;
import com.tacniz.visitormanagement.service.VisitRowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitRowServiceImpl implements VisitRowService {

    private final VisitRowRepo visitRowRepo;
    private final ObjectMapper objectMapper;
    private final VisitRowMapper visitRowMapper;

    @Override
    @Transactional
    public VisitRowDto create(VisitRowDto visitRowDto) {
        VisitRow visitRow = objectMapper.convertValue(visitRowDto, VisitRow.class);
        VisitRow saved = visitRowRepo.save(visitRow);
        return objectMapper.convertValue(saved, VisitRowDto.class);
    }

    @Override
    public VisitRowDto getById(Long id) {
        VisitRow visitRow = visitRowRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("VisitRow not found with id: " + id));
        return objectMapper.convertValue(visitRow, VisitRowDto.class);
    }

    @Override
    public List<VisitRowDto> getAll() {
        return visitRowRepo.findAll().stream()
                .map(visitRow -> objectMapper.convertValue(visitRow, VisitRowDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public VisitRowDto update(Long id, VisitRowDto visitRowDto) {
        VisitRow existing = visitRowRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("VisitRow not found with id: " + id));

        objectMapper.convertValue(visitRowDto, VisitRow.class);
        VisitRow updated = visitRowRepo.save(existing);
        return objectMapper.convertValue(updated, VisitRowDto.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        VisitRow visitRow = visitRowRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("VisitRow not found with id: " + id));
        visitRowRepo.delete(visitRow);
    }

    @Override
    public List<VisitRowDto> getByVisitOptionId(Long visitOptionId) {
        return visitRowRepo.findByVisitOptionId(visitOptionId).stream()
                .map(visitRow -> visitRowMapper.toDto(visitRow))
                .collect(Collectors.toList());
    }

    @Override
    public List<VisitRowDto> getByTimeRangeId(Long timeRangeId) {
        return visitRowRepo.findByTimeRangeId(timeRangeId).stream()
                .map(visitRow -> objectMapper.convertValue(visitRow, VisitRowDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<VisitRowDto> getByDateRange(LocalDate startDate, LocalDate endDate) {
        return visitRowRepo.findByDateBetween(startDate, endDate).stream()
                .map(visitRow -> objectMapper.convertValue(visitRow, VisitRowDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<VisitRowDto> getAfterDate(LocalDate date) {
        return visitRowRepo.findByDateAfter(date).stream()
                .map(visitRow -> objectMapper.convertValue(visitRow, VisitRowDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<VisitRowDto> getAfterDateAndByVisitOptionId(LocalDate date, Long visitOptionId) {
        return visitRowRepo.findByDateAfterAndVisitOptionId(date, visitOptionId).stream()
                .map(visitRow -> visitRowMapper.toDto(visitRow))
                .collect(Collectors.toList());
    }
}
