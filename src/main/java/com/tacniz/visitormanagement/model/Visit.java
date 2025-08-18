package com.tacniz.visitormanagement.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    @OneToMany(mappedBy = "visit", cascade = CascadeType.ALL, orphanRemoval = false)
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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_point_id")
    @JsonIgnore
    private ServicePoint servicePoint;

    @OneToMany(mappedBy = "visit", cascade = CascadeType.ALL)
    private List<SpecialNote> specialNotes;

    @ManyToOne
    @JoinColumn(name = "entered_gate")
    private Gate enteredGate;

    @ManyToOne
    @JoinColumn(name = "exit_gate")
    private Gate exitGate;

    private LocalDateTime exitTime;

    public void addDynamicAnswer(DynamicAnswer dynamicAnswer){
        if(this.dynamicAnswers == null){
            throw new IllegalArgumentException("Visit : no dynamic Answers found");
        }
        this.dynamicAnswers.add(dynamicAnswer);
        dynamicAnswer.setVisit(this);
    }
}
