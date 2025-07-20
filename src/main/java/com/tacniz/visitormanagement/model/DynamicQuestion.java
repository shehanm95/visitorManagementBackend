package com.tacniz.visitormanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dynamic_questions")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class DynamicQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_option_id")
    @JsonIgnore
    @ToString.Exclude
    private VisitOption visitOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_point_id")
    @JsonIgnore
    @ToString.Exclude
    private ServicePoint servicePoint;

    @Column(name = "question_text", nullable = false)
    private String questionText;

    @Column(name = "special_instructions")
    private String specialInstructions;

    @Column(name = "is_required", nullable = false)
    private Boolean isRequired;

    @Enumerated(EnumType.STRING)
    @Column(name = "answer_type", nullable = false)
    private AnswerType answerType;

    @OneToMany(
            mappedBy = "dynamicQuestion",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @ToString.Exclude
    @JsonIgnore
    private List<ButtonAnswer> buttonAnswers;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private boolean canSelectMoreThanOne;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "question_references",
            joinColumns = @JoinColumn(name = "child_question_id"),
            inverseJoinColumns = @JoinColumn(name = "parent_question_id")
    )
    @JsonIgnore
    @ToString.Exclude
    private List<DynamicQuestion> parentQuestions = new ArrayList<>();

    @ManyToMany(mappedBy = "parentQuestions", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private List<DynamicQuestion> referenceQuestions = new ArrayList<>();
    // Helper methods
    public void addParentQuestion(DynamicQuestion parent) {
        parentQuestions.add(parent);
        parent.getReferenceQuestions().add(this);
    }

    public void removeParentQuestion(DynamicQuestion parent) {
        parentQuestions.remove(parent);
        parent.getReferenceQuestions().remove(this);
    }
}

