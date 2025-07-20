package com.tacniz.visitormanagement.repo;

import com.tacniz.visitormanagement.model.DynamicAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DynamicAnswerRepository extends JpaRepository<DynamicAnswer,Long> {
    List<DynamicAnswer> findByVisitId(Long visitId);
}
