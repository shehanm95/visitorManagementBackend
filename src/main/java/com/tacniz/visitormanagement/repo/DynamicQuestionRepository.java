package com.tacniz.visitormanagement.repo;

import com.tacniz.visitormanagement.model.DynamicQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DynamicQuestionRepository extends JpaRepository<DynamicQuestion, Integer> {
    List<DynamicQuestion> findByVisitOptionId(Integer visitOptionId);
}