package com.tacniz.visitormanagement.service;

import com.tacniz.visitormanagement.dto.WorkingDayDto;
import com.tacniz.visitormanagement.model.WorkingDay;

import java.util.List;

public interface WorkingDayService {
    List<WorkingDayDto> getAll();

    List<WorkingDayDto> setAll(List<WorkingDayDto> workingDays);
}
