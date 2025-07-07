package com.tacniz.visitormanagement.dto;

import com.tacniz.visitormanagement.model.UserEntity;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class VisitDto {
    private Long id;
    private IdObject visitOption;
    private UserEntity visitor;
    private String imageName;
    private LocalDateTime printedDate;
    private List<DynamicAnswerDto> dynamicAnswers;
    private boolean isCanceled;
    private boolean isPrinted;
    private VisitRowDto visitRow;
    private LocalDateTime requestedDate = LocalDateTime.now();
}