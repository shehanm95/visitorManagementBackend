package com.tacniz.visitormanagement.service.impl;

import com.tacniz.visitormanagement.dto.DynamicQuestionDTO;
import com.tacniz.visitormanagement.mapper.DynamicQuestionMapper;
import com.tacniz.visitormanagement.model.DynamicQuestion;
import com.tacniz.visitormanagement.repo.DynamicQuestionRepository;
import com.tacniz.visitormanagement.service.DynamicQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DynamicQuestionServiceImpl implements DynamicQuestionService {

    @Autowired
    private DynamicQuestionRepository repository;

    @Autowired
    private DynamicQuestionMapper mapper;

    @Override
    public List<DynamicQuestionDTO> getQuestionsByVisitOptionId(Long visitOptionId) {
        return repository.findByVisitOptionId(visitOptionId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DynamicQuestionDTO saveQuestion(DynamicQuestionDTO questionDto) {
        DynamicQuestion question = mapper.toEntity(questionDto);
        DynamicQuestion savedQuestion = repository.save(question);
        return mapper.toDto(savedQuestion);
    }

    @Override
    public DynamicQuestionDTO getQuestionById(Long id) {
        DynamicQuestion question = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));
        return mapper.toDto(question);
    }

    @Override
    public DynamicQuestionDTO updateQuestion(DynamicQuestionDTO updatedQuestion) {
        if(updatedQuestion.getId() == null) throw new IllegalArgumentException("id not exist in the Dynamic Question");
       DynamicQuestion dynamicQuestion = repository.findById(updatedQuestion.getId()).orElseThrow(()-> new IllegalArgumentException("Dynamic Question not found in the database"));
       dynamicQuestion = mapper.toEntity(updatedQuestion);
       return mapper.toDto(repository.save(dynamicQuestion));
    }

    @Override
    public void deleteQuestion(Long id) {
        if(!repository.existsById(id)){
            throw new IllegalArgumentException("id not exist in the Dynamic Question");
        }
        repository.deleteById(id);

    }

    @Override
    public List<DynamicQuestionDTO> getAll() {
        return repository.findAll().stream().map(q->mapper.toDto(q)).toList();
    }
}