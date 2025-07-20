package com.tacniz.visitormanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "button_answers")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ButtonAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dynamic_question_id", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private DynamicQuestion dynamicQuestion;

    @Column(name = "button_text", nullable = false)
    private String buttonText;

    @ManyToMany(mappedBy = "selectedButtonAnswers")
    @JsonIgnore
    @ToString.Exclude
    private List<DynamicAnswer> dynamicAnswers = new ArrayList<>();

    public void addDynamicAnswer(DynamicAnswer dynamicAnswer){
        if(this.dynamicAnswers == null){
            this.dynamicAnswers = new ArrayList<>();
        }
        this.dynamicAnswers.add(dynamicAnswer);
    }
}
