package com.tacniz.visitormanagement.repo;

import com.tacniz.visitormanagement.model.VisitOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitOptionRepository extends JpaRepository<VisitOption, Long> {
    List<VisitOption> findByVisitTypeId(Long visitTypeId);
}
