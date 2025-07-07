package com.tacniz.visitormanagement.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.VisitDto;
import com.tacniz.visitormanagement.model.Visit;
import com.tacniz.visitormanagement.model.DynamicAnswer;
import com.tacniz.visitormanagement.repo.VisitRepository;
import com.tacniz.visitormanagement.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public VisitDto createVisit(VisitDto visitDto) {
        Visit visit = objectMapper.convertValue(visitDto, Visit.class);
        visit.getDynamicAnswers().forEach(answer -> answer.setVisit(visit));
        Visit savedVisit = visitRepository.save(visit);
        return objectMapper.convertValue(savedVisit, VisitDto.class);
    }

    @Override
    public VisitDto getVisitById(Long id) {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Visit not found with id: " + id));
        return objectMapper.convertValue(visit, VisitDto.class);
    }

    @Override
    @Transactional
    public VisitDto updateVisit(Long id, VisitDto visitDto) {
        Visit existingVisit = visitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Visit not found with id: " + id));

        // Manual mapping for update
        existingVisit.setImageName(visitDto.getImageName());
        existingVisit.setBadgePrintDate(visitDto.getBadgePrintDate());

        // Handle dynamic answers update
        existingVisit.getDynamicAnswers().clear();
        existingVisit.getDynamicAnswers().addAll(visitDto.getDynamicAnswers().stream()
                .map(dto -> {
                    DynamicAnswer answer = objectMapper.convertValue(dto, DynamicAnswer.class);
                    answer.setVisit(existingVisit);
                    return answer;
                })
                .collect(Collectors.toList()));

        Visit updatedVisit = visitRepository.save(existingVisit);
        return objectMapper.convertValue(updatedVisit, VisitDto.class);
    }

    @Override
    public void deleteVisit(Long id) {
        if (!visitRepository.existsById(id)) {
            throw new IllegalArgumentException("Visit not found with id: " + id);
        }
        visitRepository.deleteById(id);
    }

    @Override
    public List<VisitDto> getVisitsByVisitOptionId(Long visitOptionId) {
        return visitRepository.findByVisitOptionId(visitOptionId).stream()
                .map(visit -> objectMapper.convertValue(visit, VisitDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<VisitDto> getVisitsByVisitorUserId(Long visitorUserId) {
        return visitRepository.findByVisitorUserId(visitorUserId).stream()
                .map(visit -> objectMapper.convertValue(visit, VisitDto.class))
                .collect(Collectors.toList());
    }
}