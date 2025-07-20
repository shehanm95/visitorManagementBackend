package com.tacniz.visitormanagement.repo;

import com.tacniz.visitormanagement.model.Gate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GateRepo extends JpaRepository<Gate, Long> {
}
