package com.tacniz.visitormanagement.repo;

import com.tacniz.visitormanagement.model.OTPObj;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OPTRepo extends JpaRepository<OTPObj, Long> {
    Optional<OTPObj> findByEmail(String email);
    void deleteByEmail(String email);
}
