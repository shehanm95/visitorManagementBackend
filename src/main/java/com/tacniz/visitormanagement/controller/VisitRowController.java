package com.tacniz.visitormanagement.controller;

import com.tacniz.visitormanagement.dto.VisitRowDto;
import com.tacniz.visitormanagement.service.VisitRowService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("visitRows")
@RequiredArgsConstructor
public class VisitRowController {

    private final VisitRowService visitRowService;

    @PostMapping
    public ResponseEntity<VisitRowDto> create(@RequestBody VisitRowDto visitRowDto) {
        return ResponseEntity.ok(visitRowService.create(visitRowDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitRowDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(visitRowService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<VisitRowDto>> getAll() {
        return ResponseEntity.ok(visitRowService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<VisitRowDto> update(
            @PathVariable Long id,
            @RequestBody VisitRowDto visitRowDto) {
        return ResponseEntity.ok(visitRowService.update(id, visitRowDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        visitRowService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Foreign key queries
    @GetMapping("/by-visit-option/{visitOptionId}")
    public ResponseEntity<List<VisitRowDto>> getByVisitOptionId(
            @PathVariable Long visitOptionId) {
        return ResponseEntity.ok(visitRowService.getByVisitOptionId(visitOptionId));
    }

    @GetMapping("/by-time-range/{timeRangeId}")
    public ResponseEntity<List<VisitRowDto>> getByTimeRangeId(
            @PathVariable Long timeRangeId) {
        return ResponseEntity.ok(visitRowService.getByTimeRangeId(timeRangeId));
    }

    // Date queries
    @GetMapping("/by-date-range")
    public ResponseEntity<List<VisitRowDto>> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(visitRowService.getByDateRange(startDate, endDate));
    }

    @GetMapping("/after-date")
    public ResponseEntity<List<VisitRowDto>> getAfterDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(visitRowService.getAfterDate(date));
    }

    // Combined query
    @GetMapping("/after-date-by-visit-option")
    public ResponseEntity<List<VisitRowDto>> getAfterDateAndByVisitOptionId(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam Long visitOptionId) {
        return ResponseEntity.ok(
                visitRowService.getAfterDateAndByVisitOptionId(date, visitOptionId));
    }
}
