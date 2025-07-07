package com.tacniz.visitormanagement.controller;

import com.tacniz.visitormanagement.dto.VisitDto;
import com.tacniz.visitormanagement.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;

    @PostMapping
    public ResponseEntity<VisitDto> createVisit(@RequestBody VisitDto visitDto) {
        VisitDto createdVisit = visitService.createVisit(visitDto);
        return new ResponseEntity<>(createdVisit, HttpStatus.CREATED);
    }



    @GetMapping("/{id}")
    public ResponseEntity<VisitDto> getVisitById(@PathVariable Long id) {
        VisitDto visitDto = visitService.getVisitById(id);
        return ResponseEntity.ok(visitDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VisitDto> updateVisit(@PathVariable Long id, @RequestBody VisitDto visitDto) {
        VisitDto updatedVisit = visitService.updateVisit(id, visitDto);
        return ResponseEntity.ok(updatedVisit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisit(@PathVariable Long id) {
        visitService.deleteVisit(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-visit-option/{visitOptionId}")
    public ResponseEntity<List<VisitDto>> getVisitsByVisitOptionId(@PathVariable Long visitOptionId) {
        List<VisitDto> visits = visitService.getVisitsByVisitOptionId(visitOptionId);
        return ResponseEntity.ok(visits);
    }

    @GetMapping("/by-visitor/{visitorUserId}")
    public ResponseEntity<List<VisitDto>> getVisitsByVisitorUserId(@PathVariable Long visitorUserId) {
        List<VisitDto> visits = visitService.getVisitsByVisitorUserId(visitorUserId);
        return ResponseEntity.ok(visits);
    }

    @GetMapping("/all")
    public  ResponseEntity<List<VisitDto>> getAll(){
        return ResponseEntity.ok(visitService.getAll());
    }

    @GetMapping("/getByRowId/{id}")
    public ResponseEntity<List<VisitDto>> getByRowId(@PathVariable Long id){
        return ResponseEntity.ok(visitService.getByRowId(id));
    }

    @PutMapping("/markAsPrinted/{id}")
    public ResponseEntity<?> markAsPrinted(@PathVariable Long id){
       if(id == null){
           throw new IllegalArgumentException("to mark as print visit id must not be null");
       }
       visitService.markAsPrinted(id);
     return ResponseEntity.ok(null);
    }
}