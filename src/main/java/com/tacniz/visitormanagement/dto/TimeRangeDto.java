package com.tacniz.visitormanagement.dto;

import com.tacniz.visitormanagement.model.VisitOption;
import com.tacniz.visitormanagement.model.VisitRow;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeRangeDto {

    private Long id;
    private IdObject visitOption;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<VisitRowDto> visitRows;
}
