package com.tacniz.visitormanagement.repo;

import com.tacniz.visitormanagement.model.WorkingDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkingDaysRepo extends JpaRepository<WorkingDay,Long> {
}
