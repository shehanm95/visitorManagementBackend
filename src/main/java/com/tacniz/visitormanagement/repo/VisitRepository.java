package com.tacniz.visitormanagement.repo;


import com.tacniz.visitormanagement.model.UserEntity;
import com.tacniz.visitormanagement.model.Visit;
import com.tacniz.visitormanagement.model.VisitRow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findByVisitOptionId(Long id);
    List<Visit> findByVisitorId(Long id);

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
}
