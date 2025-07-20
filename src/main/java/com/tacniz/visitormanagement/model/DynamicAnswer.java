package com.tacniz.visitormanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;
@Entity
@Table(name = "dynamic_answers")
@Data
public class DynamicAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dynamic_question_id", nullable = false)
    @ToString.Exclude
    private DynamicQuestion dynamicQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_id")
    @ToString.Exclude
    @JsonIgnore
    private Visit visit;

    @Enumerated(EnumType.STRING)
    private AnswerType answerType;

    private String value;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "dynamic_answer_button_answers",
            joinColumns = @JoinColumn(name = "dynamic_answer_id"),
            inverseJoinColumns = @JoinColumn(name = "button_answer_id")
    )
    private List<ButtonAnswer> selectedButtonAnswers;
}