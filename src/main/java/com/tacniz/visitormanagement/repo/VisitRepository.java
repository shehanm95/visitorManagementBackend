package com.tacniz.visitormanagement.repo;


import com.tacniz.visitormanagement.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findByVisitOptionId(Long id);
    List<Visit> findByVisitorId(Long id);

    // In your repository interface
    Page<Visit> findAll(Pageable pageable);

    List<Visit> findByPrintedDateBetweenAndIsPrintedTrue(
            LocalDateTime startOfDay,
            LocalDateTime endOfDay
    );

    // In your VisitRepository.java
    Optional<Visit> findTopByPrintedDateBetweenOrderByPrintedDateDesc(
            LocalDateTime startOfDay,
            LocalDateTime endOfDay
    );


    boolean existsByVisitRowAndVisitor(VisitRow visitRow, UserEntity visitor);

    List<Visit> findByVisitRowId(Long id);


    @Query("SELECT v FROM Visit v WHERE " +
            "(:visitType IS NULL OR v.visitOption.visitType = :visitType) AND " +
            "(:visitOption IS NULL OR v.visitOption = :visitOption) AND " +
            "(:startDate IS NULL OR v.requestedDate >= :startDate) AND " +
            "(:endDate IS NULL OR v.requestedDate <= :endDate)")
    Page<Visit> findBySearchCriteria(
            @Param("visitType") VisitType visitType,
            @Param("visitOption") VisitOption visitOption,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);
}
