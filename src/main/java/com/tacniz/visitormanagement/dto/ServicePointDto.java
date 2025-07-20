package com.tacniz.visitormanagement.dto;

import com.tacniz.visitormanagement.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServicePointDto {
    private Long id;
    private String pointName;
    private String location;
    private String pointDescription;
    private String officerInstructions;
    private String visitorInstructions;
    private IdVisitOptionObject visitOption;
    private List<DutyDto> duties;
    private List<VisitDto> visits;
    private ServicePointStatus servicePointStatus;
    private List<DynamicQuestionDTO> officerQuestions;
    private List<SpecialNoteDto> specialNotes;
    private Boolean isFrontOffice = false;
    private Boolean isHost = false;
}