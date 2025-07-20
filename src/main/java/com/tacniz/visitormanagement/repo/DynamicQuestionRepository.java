package com.tacniz.visitormanagement.repo;

import com.tacniz.visitormanagement.model.DynamicQuestion;
import com.tacniz.visitormanagement.model.ReferenceHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DynamicQuestionRepository extends JpaRepository<DynamicQuestion, Long> {
    List<DynamicQuestion> findByVisitOptionId(Long visitOptionId);

    List<DynamicQuestion> findByServicePointId(Long id);


//    // Option 1: Using the relationship directly (for single parent hierarchy)
//    List<DynamicQuestion> findByParentQuestionId(Long parentId);

    // Option 2: Using JPQL query (for multiple parents scenario)
    @Query("SELECT dq FROM DynamicQuestion dq JOIN dq.parentQuestions p WHERE p.id = :parentId")
    List<DynamicQuestion> findReferenceQuestionsByParentId(@Param("parentId") Long parentId);

    // Option 3: Native query version (if you need more control)
    @Query(value = "SELECT dq.* FROM dynamic_questions dq " +
            "JOIN question_references qr ON dq.id = qr.child_question_id " +
            "WHERE qr.parent_question_id = :parentId", nativeQuery = true)
    List<DynamicQuestion> findChildrenByParentIdNative(@Param("parentId") Long parentId);
}