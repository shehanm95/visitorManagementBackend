package com.tacniz.visitormanagement.repo;


import com.tacniz.visitormanagement.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findByVisitOptionId(Long visitOptionId);
    List<Visit> findByVisitorUserId(Long visitorUserId);
}