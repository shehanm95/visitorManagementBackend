package com.tacniz.visitormanagement.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.SpecialNoteDto;
import com.tacniz.visitormanagement.mapper.UserMapper;
import com.tacniz.visitormanagement.mapper.VisitMapper;
import com.tacniz.visitormanagement.model.SpecialNote;
import com.tacniz.visitormanagement.mapper.SpecialNoteMapper;
import com.tacniz.visitormanagement.mapper.ServicePointMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SpecialNoteMapperImpl implements SpecialNoteMapper {

    @Autowired
    @Lazy
    private ObjectMapper objectMapper;
    @Autowired
    @Lazy
    private UserMapper userMapper;
    @Autowired
    @Lazy
    private ServicePointMapper servicePointMapper;
    @Autowired
    @Lazy
    private VisitMapper visitMapper;


    @Override
    public SpecialNoteDto toDto(SpecialNote note) {
        if (note == null) return null;

        SpecialNoteDto dto = objectMapper.convertValue(note, SpecialNoteDto.class);
        dto.setServicePoint(servicePointMapper.toDto(note.getServicePoint()));
        dto.setOfficer(userMapper.toDto(note.getOfficer()));
        dto.setVisit(visitMapper.toDto(note.getVisit()));

        if (note.getReviewedBy() != null) {
            dto.setReviewedBy(note.getReviewedBy().stream()
                    .map(userMapper::toDto)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    @Override
    public SpecialNote toEntity(SpecialNoteDto dto) {
        if (dto == null) return null;

        SpecialNote note = objectMapper.convertValue(dto, SpecialNote.class);
        note.setServicePoint(servicePointMapper.toEntity(dto.getServicePoint()));
        note.setOfficer(userMapper.toEntity(dto.getOfficer()));
        note.setVisit(visitMapper.toEntity(dto.getVisit()));

        if (dto.getReviewedBy() != null) {
            note.setReviewedBy(dto.getReviewedBy().stream()
                    .map(userMapper::toEntity)
                    .collect(Collectors.toList()));
        }

        return note;
    }

    @Override
    public List<SpecialNoteDto> toDtoList(List<SpecialNote> notes) {
        if (notes == null) return null;
        return notes.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<SpecialNote> toEntityList(List<SpecialNoteDto> dtos) {
        if (dtos == null) return null;
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
