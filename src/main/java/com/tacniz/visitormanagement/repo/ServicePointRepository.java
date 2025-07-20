package com.tacniz.visitormanagement.repo;

import com.tacniz.visitormanagement.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicePointRepository extends JpaRepository<ServicePoint, Long> {

    // Basic CRUD operations are inherited from JpaRepository

    // Find by exact name match (case insensitive)
    Optional<ServicePoint> findByPointNameIgnoreCase(String pointName);

    // Find by name containing (case insensitive)
    List<ServicePoint> findByPointNameContainingIgnoreCase(String namePart);

    // Find all by status
    List<ServicePoint> findByServicePointStatus(ServicePointStatus status);

    // Find active service points
    default List<ServicePoint> findActiveServicePoints() {
        return findByServicePointStatus(ServicePointStatus.ACTIVE);
    }

    // Find by visit option
    List<ServicePoint> findByVisitOption(VisitOption visitOption);

    // Find by visit option ID (avoids fetching entire VisitOption)
    List<ServicePoint> findByVisitOptionId(Long visitOptionId);

    // Find service points with their duties (eager loading)
    @Query("SELECT sp FROM ServicePoint sp LEFT JOIN FETCH sp.duties WHERE sp.id = :id")
    Optional<ServicePoint> findByIdWithDuties(@Param("id") Long id);

    // Count visits for a service point
    @Query("SELECT COUNT(v) FROM Visit v WHERE v.servicePoint.id = :servicePointId")
    long countVisitsByServicePointId(@Param("servicePointId") Long servicePointId);

    // Check existence by name (for validation)
    boolean existsByPointNameIgnoreCase(String pointName);

    // Find front office service points
    List<ServicePoint> findByIsFrontOfficeTrue();

    // Find host service points
    List<ServicePoint> findByIsHostTrue();

    // Find by both front office and host status
    List<ServicePoint> findByIsFrontOfficeAndIsHost(Boolean isFrontOffice, Boolean isHost);

    // Find service points with special notes (eager loading)
    @Query("SELECT DISTINCT sp FROM ServicePoint sp LEFT JOIN FETCH sp.specialNotes WHERE sp.id = :id")
    Optional<ServicePoint> findByIdWithSpecialNotes(@Param("id") Long id);

    // Find service points with officer questions (eager loading)
    @Query("SELECT DISTINCT sp FROM ServicePoint sp LEFT JOIN FETCH sp.officerQuestions WHERE sp.id = :id")
    Optional<ServicePoint> findByIdWithOfficerQuestions(@Param("id") Long id);

    // Find service points with all relationships (for detailed view)
    @Query("SELECT sp FROM ServicePoint sp " +
            "LEFT JOIN FETCH sp.duties " +
            "LEFT JOIN FETCH sp.officerQuestions " +
            "LEFT JOIN FETCH sp.specialNotes " +
            "WHERE sp.id = :id")
    Optional<ServicePoint> findByIdWithAllRelations(@Param("id") Long id);
}