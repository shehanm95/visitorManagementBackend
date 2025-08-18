package com.tacniz.visitormanagement.service.impl;

import com.tacniz.visitormanagement.dto.DynamicAnswerDto;
import com.tacniz.visitormanagement.mapper.DynamicAnswerMapper;
import com.tacniz.visitormanagement.model.DynamicAnswer;
import com.tacniz.visitormanagement.repo.DynamicAnswerRepository;
import com.tacniz.visitormanagement.repo.VisitRepository;
import com.tacniz.visitormanagement.service.DynamicAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DynamicAnswerServiceImpl implements DynamicAnswerService {

    private final DynamicAnswerRepository answerRepository;
    private final DynamicAnswerMapper dynamicAnswerMapper;

    @Autowired
    @Lazy
    private final VisitRepository visitRepository;

    @Override
    public List<DynamicAnswerDto> saveAll(List<DynamicAnswerDto> dynamicAnswers) {
        List<DynamicAnswer> entityList = dynamicAnswerMapper.toEntityList(dynamicAnswers);
        return dynamicAnswerMapper.toDtoList(answerRepository.saveAll(entityList));
    }

    @Override
    public List<DynamicAnswerDto> getAnswersByVisitId(Long visitId) {
        List<DynamicAnswer> answers = answerRepository.findByVisitId(visitId);
        return dynamicAnswerMapper.toDtoList(answers);
    }
}
