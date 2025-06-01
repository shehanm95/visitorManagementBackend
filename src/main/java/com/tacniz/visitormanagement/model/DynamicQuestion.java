package com.tacniz.visitormanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "dynamic_questions")
@Data
public class DynamicQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_option_id", nullable = true)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private VisitOption visitOption;

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
    private List<ButtonAnswer> buttonAnswers;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private boolean canSelectMoreThanOne;
}
