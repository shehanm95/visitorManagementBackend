package com.tacniz.visitormanagement.service;

import com.tacniz.visitormanagement.dto.DynamicAnswerDto;

import java.util.List;

public interface DynamicAnswerService {
    List<DynamicAnswerDto> saveAll(List<DynamicAnswerDto> dynamicAnswers);

    List<DynamicAnswerDto> getAnswersByVisitId(Long visitId);
}
