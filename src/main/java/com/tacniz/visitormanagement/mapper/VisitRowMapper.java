package com.tacniz.visitormanagement.mapper;

import com.tacniz.visitormanagement.dto.VisitRowDto;
import com.tacniz.visitormanagement.model.VisitRow;

public interface VisitRowMapper {
    VisitRow toEntity (VisitRowDto visitRowDto);
    VisitRowDto toDto (VisitRow visitRow);
}
