package com.tacniz.visitormanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "email_authentication_obj")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class EmailAuthenticationObj {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String email;

    private String digits;
}
