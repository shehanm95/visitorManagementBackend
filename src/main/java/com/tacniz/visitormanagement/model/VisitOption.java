package com.tacniz.visitormanagement.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "visitOptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitOption {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "visitOptionName")
    private String visitOptionName;

    @ManyToOne
    @JoinColumn(name = "visitType", referencedColumnName = "id", nullable = false)
    private VisitType visitType;

    @Column(name = "description")
    private String description;

    @Column(name = "isPreRegistration")
    private Boolean isPreRegistration;

//    @Column(name = "preRegistration")
//    private PreRegistration preRegistrationObj; // Enum or String (e.g., "allWorkingD", "specificDay", "range")

    @Column(name = "imagePath")
    private String imageName;

    @Column(name = "isPhotoRequired")
    private Boolean isPhotoRequired;

    @Column(name = "isPhotoOptional")
    private Boolean isPhotoOptional;

    @Column(name = "isPhoneNumberRequired")
    private Boolean isPhoneNumberRequired;

    @Column(name = "isEmailRequired")
    private Boolean isEmailRequired;



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
//    @JoinColumn(name = "DynamicQuestions", referencedColumnName = "id")
//    private DynamicQuestions dynamicQuestions;
//
//    @ManyToOne
//    @JoinColumn(name = "servicePoints", referencedColumnName = "id")
//    private ServicePoints servicePoints;
}
