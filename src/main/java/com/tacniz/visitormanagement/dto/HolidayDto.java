package com.tacniz.visitormanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tacniz.visitormanagement.model.VisitOption;
import jakarta.persistence.*;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "visit_option_id", nullable = false)
    @JsonIgnore
    private VisitOption visitOption;

    private LocalDate date;

    private boolean forAll;

}
