package com.tacniz.visitormanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "visit_options") // ✅ Updated to snake_case
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "visit_option_name") // ✅ snake_case
    private String visitOptionName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_type", referencedColumnName = "id", nullable = false) // ✅ FK fix
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

    @OneToMany(mappedBy = "visitOption", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
    private List<DynamicQuestion> dynamicQuestions;

//    @ManyToOne
//    @JoinColumn(name = "allWorkingDays", referencedColumnName = "id")
//    private AllWorkingDays allWorkingDays;
//
//    @ManyToOne
//    @JoinColumn(name = "specificDay", referencedColumnName = "id")
//    private SpecificDay specificDay;
//
//    @ManyToOne
//    @JoinColumn(name = "dateRange", referencedColumnName = "id")
//    private DateRange dateRange;
//
//    @ManyToOne
//    @JoinColumn(name = "servicePoints", referencedColumnName = "id")
//    private ServicePoints servicePoints;
}
