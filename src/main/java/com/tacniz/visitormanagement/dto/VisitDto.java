package com.tacniz.visitormanagement.dto;

import com.tacniz.visitormanagement.model.Gate;
import com.tacniz.visitormanagement.model.UserEntity;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class VisitDto {
    private Long id;
    private IdVisitOptionObject visitOption;
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
    private MultipartFile image;
}