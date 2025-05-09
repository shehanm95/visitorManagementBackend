package com.tacniz.visitormanagement.service;

import com.tacniz.visitormanagement.dto.VisitTypeDTO;
import com.tacniz.visitormanagement.model.VisitType;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface VisitTypeService {

    VisitTypeDTO createVisitType(VisitTypeDTO visitTypeDTO) throws IOException;

    VisitTypeDTO getVisitTypeById(Long id);

    List<VisitTypeDTO> getAllVisitTypes();

    VisitTypeDTO updateVisitType(VisitTypeDTO visitTypeDTO) throws IOException;

    void deleteVisitType(Long id);

    VisitType deleteCover(String filename);

    ResponseEntity<Resource> getImage(String filename);
}