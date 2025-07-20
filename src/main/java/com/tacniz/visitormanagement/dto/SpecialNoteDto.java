package com.tacniz.visitormanagement.dto;


import com.tacniz.visitormanagement.model.UserEntity;
import com.tacniz.visitormanagement.model.Visit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpecialNoteDto {

    private Long id;

    private ServicePointDto servicePoint;

    private UserDto officer;

    private VisitDto visit;

    private LocalDateTime dateTime;

    private List<UserDto> reviewedBy;

    private String noteContent;
}