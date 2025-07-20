package com.tacniz.visitormanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HolidayDto {
    private Long id;
    private VisitOptionDTO visitOption;
    private LocalDate date;
    private boolean forAll;

}
