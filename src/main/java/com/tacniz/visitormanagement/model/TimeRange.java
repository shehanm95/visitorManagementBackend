package com.tacniz.visitormanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeRange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "visit_option_id")
    @JsonIgnore
    private VisitOption visitOption;

    @OneToMany(mappedBy = "timeRange")
    private List<VisitRow> visitRows;

    private LocalTime startTime;
    private LocalTime endTime;
}
