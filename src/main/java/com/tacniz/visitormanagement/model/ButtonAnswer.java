package com.tacniz.visitormanagement.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "button_answers")
@Data
public class ButtonAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dynamic_question_id", nullable = false)
    @JsonIgnore
    private DynamicQuestion dynamicQuestion;

    @Column(name = "button_text", nullable = false)
    private String buttonText;
}
