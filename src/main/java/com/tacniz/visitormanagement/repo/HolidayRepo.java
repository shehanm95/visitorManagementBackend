package com.tacniz.visitormanagement.repo;

import com.tacniz.visitormanagement.model.Gate;
import com.tacniz.visitormanagement.model.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HolidayRepo extends JpaRepository<Holiday, Long> {
    List<Holiday> findByVisitOptionId(Long id);
    List <Holiday> findByVisitOptionIdAndForAllTrue(Long id);
    List <Holiday> findByForAllTrue();

    List<Holiday> findByDateAfter(LocalDate date);
    List<Holiday> findByVisitOptionIdAndDateAfter(Long visitOptionId, LocalDate date);
    List<Holiday> findByVisitOptionIdAndForAllTrueAndDateAfter(Long visitOptionId, LocalDate date);
    List<Holiday> findByForAllTrueAndDateAfter(LocalDate date);

}