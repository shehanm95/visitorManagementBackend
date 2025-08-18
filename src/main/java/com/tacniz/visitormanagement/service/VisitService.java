package com.tacniz.visitormanagement.service;


import com.tacniz.visitormanagement.dto.*;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface VisitService {
    VisitDto createVisit(VisitDto visitDto);
    FullVisitDto getVisitById(Long id);
    VisitDto updateVisit(Long id, VisitDto visitDto);
    void deleteVisit(Long id);
    List<VisitDto> getVisitsByVisitOptionId(Long visitOptionId);
    List<VisitDto> getVisitsByVisitorUserId(Long visitorUserId);

    List<VisitDto> getAll(Integer PageLimit, Integer page);
    List<VisitRowDto> getVisitRowsForDate(LocalDate date, IdObject visitOption);

    List<VisitDto> getByRowId(Long id);

    void markAsPrinted(Long id);

    VisitDto createPreReg(VisitDto visit);

    ResponseEntity<Resource> getImage(String imageName);

    List<VisitDto> getVisitsBySearchObj(VisitSearchObject searchObject, Integer pageLimit, Integer page);

    VisitDto cancelVisit(Long id);
}