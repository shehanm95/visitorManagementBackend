package com.tacniz.visitormanagement.repo;

import com.tacniz.visitormanagement.model.OptObj;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OPTRepo extends JpaRepository<OptObj, Long> {
    Optional<OptObj> findByEmail(String email);
    void deleteByEmail(String email);
}
