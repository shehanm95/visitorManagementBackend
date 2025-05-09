package com.tacniz.visitormanagement.mapper;

import com.tacniz.visitormanagement.dto.VisitOptionDTO;
import com.tacniz.visitormanagement.model.VisitOption;
import com.tacniz.visitormanagement.model.VisitType;

public interface VisitOptionMapper {
    VisitOption toEntity (VisitOptionDTO visitOptionDTO);
    public VisitOptionDTO toDto(VisitOption visitOptionEntity);
}
