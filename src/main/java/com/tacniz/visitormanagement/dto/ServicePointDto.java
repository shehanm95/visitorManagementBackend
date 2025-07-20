package com.tacniz.visitormanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "service_point")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServicePointDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String pointName;

    private String pointDescription;

    @Column(name = "officer_instructions", columnDefinition = "TEXT")
    private String officerInstructions;

    @Column(name = "visitor_instructions", columnDefinition = "TEXT")
    private String visitorInstructions;

    @Enumerated(EnumType.STRING)
    private VisitOption visitOption;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "service_point_officers",
            joinColumns = @JoinColumn(name = "service_point_id"),
            inverseJoinColumns = @JoinColumn(name = "officer_id")
    )
    private List<UserEntity> officers;

    @OneToMany(mappedBy = "servicePoint", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Visit> visits;

    @Enumerated(EnumType.STRING)
    private ServicePointStatus servicePointStatus;

    @OneToMany(mappedBy = "servicePoint", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DynamicQuestion> officerQuestions;

    @OneToMany(mappedBy = "servicePoint", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SpecialNote> specialNotes;
}