package com.tacniz.visitormanagement.repo;

import com.tacniz.visitormanagement.model.Appointment;
import com.tacniz.visitormanagement.model.ReferenceHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferenceHolderRepo extends JpaRepository<ReferenceHolder, Long> {}
