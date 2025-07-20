package com.tacniz.visitormanagement.controller;

import com.tacniz.visitormanagement.dto.HolidayDto;
import com.tacniz.visitormanagement.service.HolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/holiday")
@RequiredArgsConstructor
public class HolidayController {

    private final HolidayService holidayService;

    @GetMapping
    public ResponseEntity<List<HolidayDto>> getAllHolidays() {
        return ResponseEntity.ok(holidayService.getAllHolidays());
    }

    @GetMapping("/future")
    public ResponseEntity<List<HolidayDto>> getAllFutureHolidays() {
        return ResponseEntity.ok(holidayService.getAllFutureHolidays());
    }

    @GetMapping("/visit-option/{visitOptionId}")
    public ResponseEntity<List<HolidayDto>> getHolidaysByVisitOption(@PathVariable Long visitOptionId) {
        return ResponseEntity.ok(holidayService.getHolidaysByVisitOption(visitOptionId));
    }

    @GetMapping("/visit-option/{visitOptionId}/future")
    public ResponseEntity<List<HolidayDto>> getFutureHolidaysByVisitOption(@PathVariable Long visitOptionId) {
        return ResponseEntity.ok(holidayService.getFutureHolidaysByVisitOption(visitOptionId));
    }

    @PostMapping
    public ResponseEntity<HolidayDto> createHoliday(@RequestBody HolidayDto holidayDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(holidayService.createHoliday(holidayDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HolidayDto> updateHoliday(@PathVariable Long id, @RequestBody HolidayDto holidayDto) {
        return ResponseEntity.ok(holidayService.updateHoliday(id, holidayDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHoliday(@PathVariable Long id) {
        holidayService.deleteHoliday(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/saveAll")
    public ResponseEntity<List<HolidayDto>> saveAll(@RequestBody List<HolidayDto> holidays){
        return ResponseEntity.ok(holidayService.saveAll(holidays));
    }
}