package com.tacniz.visitormanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reference_holder")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReferenceHolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToMany(mappedBy = "referenceHolder" , cascade = CascadeType.ALL)
//    List<DynamicQuestion> referenceQuestions = new ArrayList<>();

//    @OneToOne(mappedBy = "referenceHolder")
//    DynamicQuestion dynamicQuestion;
}
