package com.tacniz.visitormanagement.dto;

import com.tacniz.visitormanagement.model.Visit;
import com.tacniz.visitormanagement.model.VisitOption;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitRowDto {

    private Long id;

    private LocalDate date;

    private TimeRangeDto timeRange;

    private LocalTime startTime;
    private LocalTime endTime;
    private int averageTimeForAPerson;
    private int visitorsPerRow;

    private VisitOptionDTO visitOption;

    private List<VisitDto> visits= new ArrayList<>();

}