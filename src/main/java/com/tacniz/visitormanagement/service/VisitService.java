package com.tacniz.visitormanagement.service;


import com.tacniz.visitormanagement.dto.VisitDto;
import java.util.List;

public interface VisitService {
    VisitDto createVisit(VisitDto visitDto);
    VisitDto getVisitById(Long id);
    VisitDto updateVisit(Long id, VisitDto visitDto);
    void deleteVisit(Long id);
    List<VisitDto> getVisitsByVisitOptionId(Long visitOptionId);
    List<VisitDto> getVisitsByVisitorUserId(Long visitorUserId);

    List<VisitDto> getAll();


    List<VisitDto> getByRowId(Long id);

    void markAsPrinted(Long id);
}