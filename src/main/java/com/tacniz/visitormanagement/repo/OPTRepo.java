package com.tacniz.visitormanagement.repo;

import com.tacniz.visitormanagement.model.EmailAuthenticationObj;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailAuthenticationObjRepo extends JpaRepository<EmailAuthenticationObj, Long> {
    Optional<EmailAuthenticationObj> findByEmail(String email);
    void deleteByEmail(String email);
}
