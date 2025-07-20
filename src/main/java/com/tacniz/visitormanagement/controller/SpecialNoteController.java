package com.tacniz.visitormanagement.controller;

import com.tacniz.visitormanagement.dto.SpecialNoteDto;
import com.tacniz.visitormanagement.service.SpecialNoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/special-notes")
@RequiredArgsConstructor
public class SpecialNoteController {

    private final SpecialNoteService specialNoteService;

    @PostMapping
    public ResponseEntity<SpecialNoteDto> createSpecialNote(
            @Valid @RequestBody SpecialNoteDto specialNoteDto) {
        SpecialNoteDto createdNote = specialNoteService.createSpecialNote(specialNoteDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdNote.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdNote);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialNoteDto> getSpecialNoteById(@PathVariable Long id) {
        return ResponseEntity.ok(specialNoteService.getSpecialNoteById(id));
    }

    @GetMapping
    public ResponseEntity<List<SpecialNoteDto>> getAllSpecialNotes() {
        return ResponseEntity.ok(specialNoteService.getAllSpecialNotes());
    }

    @GetMapping("/service-point/{servicePointId}")
    public ResponseEntity<List<SpecialNoteDto>> getNotesByServicePoint(
            @PathVariable Long servicePointId) {
        return ResponseEntity.ok(specialNoteService.getNotesByServicePoint(servicePointId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecialNoteDto> updateSpecialNote(
            @PathVariable Long id,
            @Valid @RequestBody SpecialNoteDto specialNoteDto) {
        return ResponseEntity.ok(specialNoteService.updateSpecialNote(id, specialNoteDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecialNote(@PathVariable Long id) {
        specialNoteService.deleteSpecialNote(id);
        return ResponseEntity.noContent().build();
    }
}