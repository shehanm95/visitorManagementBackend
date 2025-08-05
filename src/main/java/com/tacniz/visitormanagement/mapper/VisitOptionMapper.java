package com.tacniz.visitormanagement.mapper;

import com.tacniz.visitormanagement.dto.IdVisitOptionObject;
import com.tacniz.visitormanagement.dto.VisitOptionDTO;
import com.tacniz.visitormanagement.model.VisitOption;
import com.tacniz.visitormanagement.model.VisitType;


public interface VisitOptionMapper {
    VisitOption toEntity (VisitOptionDTO visitOptionDTO);
    VisitOptionDTO toDto(VisitOption visitOptionEntity);

    IdVisitOptionObject toIdObject(VisitOption visitOption);

    void updateVisitOptionFromDto(VisitOptionDTO dto, VisitOption entity);

}
