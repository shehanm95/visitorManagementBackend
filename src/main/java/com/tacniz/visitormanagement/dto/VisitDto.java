package com.tacniz.visitormanagement.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class VisitDto {
    private Long id;
    private Long visitOptionId;
    private Long visitorUserId;
    private String imageName;
    private Date badgePrintDate;
    private List<DynamicAnswerDto> dynamicAnswers;
}