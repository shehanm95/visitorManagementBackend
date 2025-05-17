package com.tacniz.visitormanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "VisitType")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "visitTypeName")
    private String visitTypeName;

    @Column(name = "visitTypeDescription")
    private String visitTypeDescription;

    @Column(name = "imagePath")
    private String imageName;

    @OneToMany(mappedBy = "visitType", cascade = CascadeType.ALL, orphanRemoval = true)

    private List<VisitOption> visitOptions;
}
