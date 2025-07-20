package com.tacniz.visitormanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "duty")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Duty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "service_point_id" , nullable = false)
    @JsonIgnore
    private ServicePoint servicePoint;

    @ManyToOne
    @JoinColumn(name = "officer_id" , nullable = false)
    private UserEntity officer;

    @Enumerated(EnumType.STRING)
    private DutyState dutyState;

    private LocalDateTime AcceptedTime;



}
