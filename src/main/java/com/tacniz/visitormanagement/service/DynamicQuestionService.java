package com.tacniz.visitormanagement.service;

import com.tacniz.visitormanagement.model.DynamicQuestion;
import java.util.List;

public interface DynamicQuestionService {
    List<DynamicQuestion> getQuestionsByVisitOptionId(Integer visitOptionId);
    DynamicQuestion saveQuestion(DynamicQuestion question);
    DynamicQuestion getQuestionById(Integer id);
}