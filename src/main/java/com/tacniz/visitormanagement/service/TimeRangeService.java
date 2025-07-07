package com.tacniz.visitormanagement.service;

import com.tacniz.visitormanagement.dto.TimeRangeDto;

import java.util.List;

public interface TimeRangeService {

    // Create
    TimeRangeDto createTimeRange(TimeRangeDto timeRangeDto);

    // Read
    TimeRangeDto getTimeRangeById(Long id);
    List<TimeRangeDto> getAllTimeRanges();
    List<TimeRangeDto> getTimeRangesByVisitOptionId(Long visitOptionId);

    // Update
    TimeRangeDto updateTimeRange(Long id, TimeRangeDto timeRangeDto);

    // Delete
    void deleteTimeRange(Long id);

    // Additional business methods can be declared here
}