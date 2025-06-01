package com.tacniz.visitormanagement.service;

import com.tacniz.visitormanagement.dto.DynamicQuestionDTO;

import java.util.List;

public interface DynamicQuestionService {
    List<DynamicQuestionDTO> getQuestionsByVisitOptionId(Long visitOptionId);
    DynamicQuestionDTO saveQuestion(DynamicQuestionDTO question);
    DynamicQuestionDTO getQuestionById(Long id);

    DynamicQuestionDTO updateQuestion(DynamicQuestionDTO updatedQuestion);

    void deleteQuestion(Long id);

    List<DynamicQuestionDTO> getAll();
}