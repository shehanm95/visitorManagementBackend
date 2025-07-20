package com.tacniz.visitormanagement.dto;

import com.tacniz.visitormanagement.model.VisitOption;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitRowReq {
    @NotNull(message = "visit date cannot be null")
    private LocalDate date;
    @NotNull(message = "visit option cannot be null")
    private IdObject visitOption;
}
