package com.tacniz.visitormanagement.dto;

import com.tacniz.visitormanagement.model.VisitOption;
import com.tacniz.visitormanagement.model.VisitType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VisitSearchObject {
    private VisitType visitType;
    private VisitOption visitOption;
    private LocalDate  startDate ;
    private LocalDate  endDate ;
}

