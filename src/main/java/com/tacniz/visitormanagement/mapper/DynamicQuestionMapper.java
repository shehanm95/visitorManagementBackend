package com.tacniz.visitormanagement.mapper;

import com.tacniz.visitormanagement.dto.DynamicQuestionDTO;
import com.tacniz.visitormanagement.model.DynamicQuestion;

public interface DynamicQuestionMapper {
    DynamicQuestion toEntity(DynamicQuestionDTO dto);
    DynamicQuestionDTO toDto(DynamicQuestion entity);
}