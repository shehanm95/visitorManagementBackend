package com.tacniz.visitormanagement.mapper;

import com.tacniz.visitormanagement.dto.SpecialNoteDto;
import com.tacniz.visitormanagement.model.SpecialNote;

import java.util.List;

public interface SpecialNoteMapper {

    SpecialNoteDto toDto(SpecialNote note);

    SpecialNote toEntity(SpecialNoteDto dto);

    List<SpecialNoteDto> toDtoList(List<SpecialNote> notes);

    List<SpecialNote> toEntityList(List<SpecialNoteDto> dtos);
}
