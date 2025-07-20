package com.tacniz.visitormanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "special_notes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpecialNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_point_id", nullable = false)
    private ServicePoint servicePoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "officer_id", nullable = false)
    private UserEntity officer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_id")
    private Visit visit;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "note_reviewers",
            joinColumns = @JoinColumn(name = "note_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> reviewedBy;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String noteContent;
}