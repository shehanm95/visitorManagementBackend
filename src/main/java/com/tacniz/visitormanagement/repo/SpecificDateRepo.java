package com.tacniz.visitormanagement.repo;

import com.tacniz.visitormanagement.model.SpecificDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpecificDateRepo extends JpaRepository<SpecificDate,Long> {
    List<SpecificDate> findAllByVisitOptionId(Long id);
}
