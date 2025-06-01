package com.tacniz.visitormanagement.service.impl;

package com.tacniz.visitormanagement.service;

import com.tacniz.visitormanagement.model.DynamicQuestion
import com.tacniz.visitormanagement.repo.DynamicQuestionRepository;
import com.tacniz.visitormanagement.service.DynamicQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DynamicQuestionServiceImpl implements DynamicQuestionService {

    @Autowired
    private DynamicQuestionRepository repository;

    @Override
    public List<DynamicQuestion> getQuestionsByVisitOptionId(Integer visitOptionId) {
        return repository.findByVisitOptionId(visitOptionId);
    }

    @Override
    public DynamicQuestion saveQuestion(DynamicQuestion question) {
        return repository.save(question);
    }

    @Override
    public DynamicQuestion getQuestionById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));
    }
}