package com.tacniz.visitormanagement.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GateDto {
    private Long id;
    private String gateName;


    private List<VisitDto> enteredVisits;


    private List<VisitDto> exitGate;
}
