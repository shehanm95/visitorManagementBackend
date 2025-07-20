package com.tacniz.visitormanagement.model;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gates")
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Gate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Gate name cannot be blank")
    @Size(max = 100, message = "Gate name must be less than 100 characters")
    @Column(name = "gate_name", nullable = false, length = 100)
    private String gateName;

    @OneToMany(mappedBy = "enteredGate" , cascade = CascadeType.ALL)
    private List<Visit> enteredVisits = new ArrayList<>();

    @OneToMany(mappedBy = "exitGate", cascade = CascadeType.ALL)
    private List<Visit> exitGate = new ArrayList<>();

}

