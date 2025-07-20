package com.tacniz.visitormanagement.repo;

import com.tacniz.visitormanagement.model.ServicePoint;
import com.tacniz.visitormanagement.model.ServicePointStatus;
import com.tacniz.visitormanagement.model.VisitOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicePointRepository extends JpaRepository<ServicePoint, Long> {

    // Find by name (case insensitive)
    Optional<ServicePoint> findByPointNameIgnoreCase(String pointName);

    // Find all active service points
    List<ServicePoint> findByServicePointStatus(ServicePointStatus status);

    // Find service points by officer ID
    @Query("SELECT sp FROM ServicePoint sp JOIN sp.officers o WHERE o.id = :officerId")
    List<ServicePoint> findByOfficerId(@Param("officerId") Long officerId);

    // Find service points with visit option
    List<ServicePoint> findByVisitOption(VisitOption visitOption);

    // Count visits for a service point
    @Query("SELECT COUNT(v) FROM Visit v WHERE v.servicePoint.id = :servicePointId")
    long countVisitsByServicePointId(@Param("servicePointId") Long servicePointId);

    // Check if service point exists by name (for validation)
    boolean existsByPointNameIgnoreCase(String pointName);

    // Find service points with dynamic questions
    @Query("SELECT DISTINCT sp FROM ServicePoint sp JOIN FETCH sp.officerQuestions WHERE sp.id IN :ids")
    List<ServicePoint> findWithQuestionsByIds(@Param("ids") List<Long> ids);
}
