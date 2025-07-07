package com.tacniz.visitormanagement.repo;

import com.tacniz.visitormanagement.model.TimeRange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface TimeRangeRepository extends JpaRepository<TimeRange, Long> {

    // Basic CRUD operations are already provided by JpaRepository:
    // save(), findById(), findAll(), deleteById(), etc.

    // Custom query methods
    List<TimeRange> findByVisitOptionId(Long visitOptionId);

    List<TimeRange> findByStartTimeGreaterThanEqual(LocalTime time);

    List<TimeRange> findByEndTimeLessThanEqual(LocalTime time);

    List<TimeRange> findByStartTimeBetween(LocalTime start, LocalTime end);

    @Query("SELECT tr FROM TimeRange tr WHERE tr.visitOption.id = :visitOptionId AND tr.startTime >= :startTime")
    List<TimeRange> findByVisitOptionAndStartTimeAfter(
            @Param("visitOptionId") Long visitOptionId,
            @Param("startTime") LocalTime startTime);

    @Query("SELECT tr FROM TimeRange tr WHERE tr.visitOption.id = :visitOptionId AND tr.endTime <= :endTime")
    List<TimeRange> findByVisitOptionAndEndTimeBefore(
            @Param("visitOptionId") Long visitOptionId,
            @Param("endTime") LocalTime endTime);

    @Query("SELECT tr FROM TimeRange tr WHERE tr.visitOption.id = :visitOptionId AND tr.startTime <= :time AND tr.endTime >= :time")
    List<TimeRange> findByVisitOptionAndTimeBetween(
            @Param("visitOptionId") Long visitOptionId,
            @Param("time") LocalTime time);

    // For batch operations
    List<TimeRange> findByVisitOptionIdIn(List<Long> visitOptionIds);

    // Count methods
    long countByVisitOptionId(Long visitOptionId);

    // Exists methods
    boolean existsByVisitOptionId(Long visitOptionId);

    // Delete methods
    void deleteByVisitOptionId(Long visitOptionId);


}
