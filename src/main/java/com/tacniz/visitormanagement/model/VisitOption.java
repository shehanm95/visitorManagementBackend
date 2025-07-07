package com.tacniz.visitormanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tacniz.visitormanagement.dto.SpecificDateDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "visit_options")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "visit_option_name")
    private String visitOptionName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_type", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private VisitType visitType;

    @Column(name = "description")
    private String description;

    @Column(name = "is_pre_registration")
    private Boolean isPreRegistration;

    @Column(name = "image_path")
    private String imageName;

    @Column(name = "is_photo_required")
    private Boolean isPhotoRequired;

    @Column(name = "is_photo_optional")
    private Boolean isPhotoOptional;

    @Column(name = "is_phone_number_required")
    private Boolean isPhoneNumberRequired;

    @Column(name = "is_email_required")
    private Boolean isEmailRequired;

    @OneToMany(mappedBy = "visitOption", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<DynamicQuestion> dynamicQuestions = new ArrayList<>();

    private int averageTimeForAPerson;
    private int visitorsPerRow;

    private boolean isActive;

    @OneToMany(mappedBy = "visitOption")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<VisitRow> visitRows = new ArrayList<>();

    @OneToMany(mappedBy = "visitOption", cascade = CascadeType.ALL, orphanRemoval = true)
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    @JsonIgnore
    private List<TimeRange> timeRanges = new ArrayList<>();

    private VisitDateType visitDateType;

    @OneToMany(mappedBy = "visitOption", cascade = CascadeType.ALL, orphanRemoval = true)
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
    private List<SpecificDate> specificDates;

//    @ManyToOne
//    @JoinColumn(name = "servicePoints", referencedColumnName = "id")
//    private ServicePoints servicePoints;
}
