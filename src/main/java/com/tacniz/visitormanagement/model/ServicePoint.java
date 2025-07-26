package com.tacniz.visitormanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "service_point")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) solution 1
public class ServicePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String pointName;

    @Column(nullable = false)
    private String location;

    private String pointDescription;

    @Column(name = "officer_instructions", columnDefinition = "TEXT")
    private String officerInstructions;

    @Column(name = "visitor_instructions", columnDefinition = "TEXT")
    private String visitorInstructions;

    @ManyToOne
    @JoinColumn(name = "visit_option_id", nullable = false)
    private VisitOption visitOption;


    @OneToMany(mappedBy = "servicePoint" , cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Duty> duties = new ArrayList<>();

    @OneToMany(mappedBy = "servicePoint", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Visit> visits;

    @Enumerated(EnumType.STRING)
    private ServicePointStatus servicePointStatus;

    @OneToMany(mappedBy = "servicePoint", cascade = CascadeType.ALL, orphanRemoval = true)

    private List<DynamicQuestion> officerQuestions;


    @OneToMany(mappedBy = "servicePoint", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<SpecialNote> specialNotes;

    @Column(name = "is_frontOffice")
    private Boolean isFrontOffice;

    @Column(name = "is_host")
    private Boolean isHost;

    public void addDuty(Duty duty) {
        if (duties == null) {
            duties = new ArrayList<>();
        }
        if (!duties.contains(duty)) {
            duties.add(duty);
            duty.setServicePoint(this);
        }
    }

}