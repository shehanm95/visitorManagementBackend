package com.tacniz.visitormanagement.service;


import com.tacniz.visitormanagement.dto.SpecialNoteDto;
import java.util.List;

public interface SpecialNoteService {
    SpecialNoteDto createSpecialNote(SpecialNoteDto specialNoteDto);
    SpecialNoteDto getSpecialNoteById(Long id);
    List<SpecialNoteDto> getAllSpecialNotes();
    List<SpecialNoteDto> getNotesByServicePoint(Long servicePointId);
    SpecialNoteDto updateSpecialNote(Long id, SpecialNoteDto specialNoteDto);
    void deleteSpecialNote(Long id);
}