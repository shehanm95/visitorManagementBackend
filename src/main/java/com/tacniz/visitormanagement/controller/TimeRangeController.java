package com.tacniz.visitormanagement.controller;

import com.tacniz.visitormanagement.dto.TimeRangeDto;
import com.tacniz.visitormanagement.service.TimeRangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/time-ranges")
public class TimeRangeController {

    private final TimeRangeService timeRangeService;

    @Autowired
    public TimeRangeController(TimeRangeService timeRangeService) {
        this.timeRangeService = timeRangeService;
    }

    // Create
    @PostMapping
    public ResponseEntity<TimeRangeDto> createTimeRange(@RequestBody TimeRangeDto timeRangeDto) {
        TimeRangeDto createdTimeRange = timeRangeService.createTimeRange(timeRangeDto);
        return new ResponseEntity<>(createdTimeRange, HttpStatus.CREATED);
    }

    // Read (single)
    @GetMapping("/{id}")
    public ResponseEntity<TimeRangeDto> getTimeRangeById(@PathVariable Long id) {
        TimeRangeDto timeRangeDto = timeRangeService.getTimeRangeById(id);
        return ResponseEntity.ok(timeRangeDto);
    }

    // Read (all)
    @GetMapping
    public ResponseEntity<List<TimeRangeDto>> getAllTimeRanges() {
        List<TimeRangeDto> timeRanges = timeRangeService.getAllTimeRanges();
        return ResponseEntity.ok(timeRanges);
    }

    // Read by VisitOption ID
    @GetMapping("/by-visit-option/{visitOptionId}")
    public ResponseEntity<List<TimeRangeDto>> getTimeRangesByVisitOptionId(@PathVariable Long visitOptionId) {
        List<TimeRangeDto> timeRanges = timeRangeService.getTimeRangesByVisitOptionId(visitOptionId);
        return ResponseEntity.ok(timeRanges);
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<TimeRangeDto> updateTimeRange(
            @PathVariable Long id,
            @RequestBody TimeRangeDto timeRangeDto) {
        TimeRangeDto updatedTimeRange = timeRangeService.updateTimeRange(id, timeRangeDto);
        return ResponseEntity.ok(updatedTimeRange);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimeRange(@PathVariable Long id) {
        timeRangeService.deleteTimeRange(id);
        return ResponseEntity.noContent().build();
    }

}
