package com.tacniz.visitormanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "visit_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "visit_type_name")
    private String visitTypeName;

    @Column(name = "visit_type_description")
    private String visitTypeDescription;

    @Column(name = "image_path")
    private String imageName;

    @OneToMany(mappedBy = "visitType", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<VisitOption> visitOptions;
}
