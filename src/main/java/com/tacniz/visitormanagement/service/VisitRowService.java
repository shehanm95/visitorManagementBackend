package com.tacniz.visitormanagement.service;

import com.tacniz.visitormanagement.dto.VisitRowDto;

import java.time.LocalDate;
import java.util.List;

public interface VisitRowService {
    VisitRowDto create(VisitRowDto visitRowDto);
    VisitRowDto getById(Long id);
    List<VisitRowDto> getAll();
    VisitRowDto update(Long id, VisitRowDto visitRowDto);
    void delete(Long id);

    // Foreign key queries
    List<VisitRowDto> getByVisitOptionId(Long visitOptionId);
    List<VisitRowDto> getByTimeRangeId(Long timeRangeId);

    // Date queries
    List<VisitRowDto> getByDateRange(LocalDate startDate, LocalDate endDate);
    List<VisitRowDto> getAfterDate(LocalDate date);

    // Combined query example
    List<VisitRowDto> getAfterDateAndByVisitOptionId(LocalDate date, Long visitOptionId);
}
