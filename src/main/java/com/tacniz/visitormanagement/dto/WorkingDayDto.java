package com.tacniz.visitormanagement.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkingDayDto {
    private Long id;

    private String dateName;

    @Column(name = "is_working")
    private boolean isWorking;
}

