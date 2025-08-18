package com.tacniz.visitormanagement.repo;

import com.tacniz.visitormanagement.model.VisitOption;
import com.tacniz.visitormanagement.model.VisitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VisitOptionRepository extends JpaRepository<VisitOption, Long> {
    List<VisitOption> findByVisitTypeId(Long visitTypeId);
    List<VisitOption> findByVisitTypeIdAndIsActiveTrue(Long visitTypeId);
    List<VisitOption> findByVisitTypeIdAndIsPreRegistrationTrueAndIsActiveTrue(Long visitTypeId);
    @Query("SELECT vo.visitType FROM VisitOption vo WHERE vo.id = :id")
    Optional<VisitType> findVisitTypeByVisitOptionId(@Param("id") Long id);

    @Query("""
SELECT DISTINCT vo.visitType 
FROM VisitOption vo 
WHERE vo.isPreRegistration = TRUE 
AND vo.isActive = TRUE
AND vo.isPreRegistration IS NOT NULL 
AND vo.isActive IS NOT NULL
""")
    List<VisitType> findVisitTypesWithPreRegistrationAndActive();


}
