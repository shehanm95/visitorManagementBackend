package com.tacniz.visitormanagement.repo;


import com.tacniz.visitormanagement.model.VisitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitTypeRepo extends JpaRepository<VisitType, Long> {
}