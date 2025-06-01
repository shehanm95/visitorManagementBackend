package com.tacniz.visitormanagement.mapper;

import com.tacniz.visitormanagement.dto.VisitTypeDTO;
import com.tacniz.visitormanagement.model.VisitType;

public interface VisitTypeMapper {
    public VisitTypeDTO toDto (VisitType visitType);
    public VisitType toEntity (VisitTypeDTO visitType);
}
