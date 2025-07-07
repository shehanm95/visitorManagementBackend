package com.tacniz.visitormanagement.repo;

import com.tacniz.visitormanagement.model.VisitRow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;

public interface VisitRowRepo extends JpaRepository<VisitRow, Long> {
    // Find by VisitOption ID
    List<VisitRow> findByVisitOptionId(Long visitOptionId);

    // Find by TimeRange ID
    List<VisitRow> findByTimeRangeId(Long timeRangeId);
    List<VisitRow> findByDateAndVisitOptionId(LocalDate date, Long visitOptionId);
    // Find by date range
    List<VisitRow> findByDateBetween(LocalDate startDate, LocalDate endDate);

    // Find all after specific date
    List<VisitRow> findByDateAfter(LocalDate date);

    // Custom query for more complex filtering
    @Query("SELECT vr FROM VisitRow vr WHERE vr.date >= :date AND vr.visitOption.id = :visitOptionId")
    List<VisitRow> findByDateAfterAndVisitOptionId(
            @Param("date") LocalDate date,
            @Param("visitOptionId") Long visitOptionId
    );
}