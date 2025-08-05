package com.tacniz.visitormanagement.repo;


import com.tacniz.visitormanagement.model.VisitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VisitTypeRepo extends JpaRepository<VisitType, Long> {

    @Query("SELECT DISTINCT vt " +
            "FROM VisitType vt " +
            "JOIN vt.visitOptions vo " +
            "WHERE vo.isPreRegistration = true")
    List<VisitType> findVisitTypesWithPreRegistrationOptions();
}