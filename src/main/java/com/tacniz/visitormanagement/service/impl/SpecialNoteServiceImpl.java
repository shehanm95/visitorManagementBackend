package com.tacniz.visitormanagement.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.SpecialNoteDto;
import com.tacniz.visitormanagement.model.SpecialNote;
import com.tacniz.visitormanagement.repo.SpecialNoteRepository;
import com.tacniz.visitormanagement.service.SpecialNoteService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecialNoteServiceImpl implements SpecialNoteService {

    private final SpecialNoteRepository specialNoteRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public SpecialNoteDto createSpecialNote(SpecialNoteDto specialNoteDto) {
        SpecialNote specialNote = convertToEntity(specialNoteDto);
        SpecialNote savedNote = specialNoteRepository.save(specialNote);
        return convertToDto(savedNote);
    }

    @Override
    @Transactional(readOnly = true)
    public SpecialNoteDto getSpecialNoteById(Long id) {
        SpecialNote specialNote = specialNoteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SpecialNote not found with id: " + id));
        return convertToDto(specialNote);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpecialNoteDto> getAllSpecialNotes() {
        return specialNoteRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpecialNoteDto> getNotesByServicePoint(Long servicePointId) {
        return specialNoteRepository.findByServicePointId(servicePointId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SpecialNoteDto updateSpecialNote(Long id, SpecialNoteDto specialNoteDto) {
        SpecialNote existingNote = specialNoteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SpecialNote not found with id: " + id));

        SpecialNote updatedNote = convertToEntity(specialNoteDto);
        updatedNote.setId(existingNote.getId());

        SpecialNote savedNote = specialNoteRepository.save(updatedNote);
        return convertToDto(savedNote);
    }

    @Override
    @Transactional
    public void deleteSpecialNote(Long id) {
        if (!specialNoteRepository.existsById(id)) {
            throw new EntityNotFoundException("SpecialNote not found with id: " + id);
        }
        specialNoteRepository.deleteById(id);
    }

    private SpecialNoteDto convertToDto(SpecialNote specialNote) {
        return objectMapper.convertValue(specialNote, SpecialNoteDto.class);
    }

    private SpecialNote convertToEntity(SpecialNoteDto specialNoteDto) {
        return objectMapper.convertValue(specialNoteDto, SpecialNote.class);
    }
}