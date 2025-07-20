package com.tacniz.visitormanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tacniz.visitormanagement.dto.TimeRangeDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class VisitRow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private LocalTime startTime;
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "time_range_id")
    @JsonIgnore
    private TimeRange timeRange;

    private int averageTimeForAPerson;
    private int visitorsPerRow;

    @ManyToOne
    @JoinColumn(name = "visit_option_id")
    @JsonIgnore
    private VisitOption visitOption;

    @OneToMany(mappedBy = "visitRow" , fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Visit> visits= new ArrayList<>();


    public void addVisit(Visit visit) {
        visits.add(visit);
        visit.setVisitRow(this);
    }

    public void removeVisit(Visit visit) {
        visits.remove(visit);
        visit.setVisitRow(null);
    }
}
