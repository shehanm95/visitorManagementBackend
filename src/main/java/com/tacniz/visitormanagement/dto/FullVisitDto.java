package com.tacniz.visitormanagement.dto;

import com.tacniz.visitormanagement.model.UserEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FullVisitDto {
    private Long id;
    private VisitOptionDTO visitOption;
    private UserDto visitor;
    private String imageName;
    private LocalDateTime printedDate;
    private List<DynamicAnswerDto> dynamicAnswers;
    private boolean isCanceled;
    private boolean isPrinted;
    private VisitRowDto visitRow;
    private LocalDateTime requestedDate = LocalDateTime.now();
    private GateDto enteredGate;
    private GateDto exitGate;
    private LocalDateTime exitTime;
}