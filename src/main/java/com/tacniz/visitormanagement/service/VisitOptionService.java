package com.tacniz.visitormanagement.service;

import com.tacniz.visitormanagement.dto.VisitOptionDTO;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VisitOptionService {

    VisitOptionDTO createVisitOption(VisitOptionDTO visitOptionDTO);

    VisitOptionDTO getVisitOptionById(Long id);

    List<VisitOptionDTO> getAllVisitOptions();

    List<VisitOptionDTO> getVisitOptionsByVisitType(Long visitTypeId);

    VisitOptionDTO updateVisitOption( VisitOptionDTO visitOptionDTO);

    void deleteVisitOption(Long id);

    ResponseEntity<Resource> getImage(String filename);

   VisitOptionDTO deleteCover(String fileName);
}