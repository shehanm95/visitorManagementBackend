package com.tacniz.visitormanagement.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "visits")
@Data
@NoArgsConstructor
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "visit_option_id", nullable = false)
    private VisitOption visitOption;

    @ManyToOne
    @JoinColumn(name = "visitor_id", nullable = false)
    private UserEntity visitor;

    private String imageName;

    @OneToMany(mappedBy = "visit", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<DynamicAnswer> dynamicAnswers = new ArrayList<>();

    @Column(name = "printed_date")
    private LocalDateTime printedDate;

    @Column(name = "is_canceled")
    private boolean isCanceled;

    private boolean isPrinted =false;

    @ManyToOne
    @JoinColumn(name = "visit_row_id")
    private VisitRow visitRow;

    //this will send the date user wants to set the appointment
    //visit row will set with this
    //and in saving this will set to the today
    private LocalDateTime requestedDate;
}
