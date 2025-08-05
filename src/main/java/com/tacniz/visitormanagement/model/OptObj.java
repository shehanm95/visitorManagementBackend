package com.tacniz.visitormanagement.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "email_authentication_obj")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptObj {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String email;

    private String digits;
}
