package com.tacniz.visitormanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@Table(name = "specific_dates")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SpecificDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "visit_option_id")
    @JsonIgnore
    private VisitOption visitOption;

}
