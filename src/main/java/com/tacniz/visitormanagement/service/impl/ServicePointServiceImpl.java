package com.tacniz.visitormanagement.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.ServicePointDto;
import com.tacniz.visitormanagement.mapper.DynamicQuestionMapper;
import com.tacniz.visitormanagement.mapper.ServicePointMapper;
import com.tacniz.visitormanagement.model.*;
import com.tacniz.visitormanagement.repo.DutyRepository;
import com.tacniz.visitormanagement.repo.DynamicQuestionRepository;
import com.tacniz.visitormanagement.repo.ServicePointRepository;
import com.tacniz.visitormanagement.repo.VisitOptionRepository;
import com.tacniz.visitormanagement.service.ServicePointService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServicePointServiceImpl implements ServicePointService {

    private final ServicePointRepository servicePointRepository;
    private final ObjectMapper objectMapper;
    private final DutyRepository dutyRepository;
    private final ServicePointMapper servicePointMapper;


    private final VisitOptionRepository visitOptionRepository;
    private final DynamicQuestionMapper questionMapper;

    @Override
    public ServicePointDto createServicePoint(ServicePointDto servicePointDto) {
        VisitOption visitOption = visitOptionRepository.findById(servicePointDto.getVisitOption().getId())
                .orElseThrow(() -> new IllegalArgumentException("Visit option not found"));


        ServicePoint servicePoint = objectMapper.convertValue(servicePointDto, ServicePoint.class);

        List<DynamicQuestion> officerQuestions = servicePointDto.getOfficerQuestions().stream().map(questionMapper::toEntity).toList();
        officerQuestions.forEach(oq->oq.setServicePoint(servicePoint));
        servicePoint.setOfficerQuestions(officerQuestions);

        List<Duty> duties = servicePoint.getDuties();
        servicePoint.setDuties(new ArrayList<>());
        ServicePoint savedServicePoint = servicePointRepository.save(servicePoint);

        duties.forEach(duty -> duty.setServicePoint(savedServicePoint));
        dutyRepository.saveAll(duties);

        return objectMapper.convertValue(savedServicePoint, ServicePointDto.class);
    }



    @Override
    @Transactional(readOnly = true)
    public ServicePointDto getServicePointById(Long id) {
        ServicePoint servicePoint = servicePointRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ServicePoint not found with id: " + id));

        return servicePointMapper.toDto(servicePoint);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServicePointDto> getAllServicePoints() {
        return servicePointMapper.toDtoList(servicePointRepository.findAll());
    }

    @Override
    @Transactional
    public ServicePointDto updateServicePoint(Long id, ServicePointDto servicePointDto) {
        ServicePoint existingServicePoint = servicePointRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ServicePoint not found with id: " + id));

        ServicePoint updatedServicePoint = servicePointMapper.toEntity(servicePointDto);
        updatedServicePoint.setId(existingServicePoint.getId());

        ServicePoint savedServicePoint = servicePointRepository.save(updatedServicePoint);
        return servicePointMapper.toDto(savedServicePoint);
    }

    @Override
    @Transactional
    public void deleteServicePoint(Long id) {
        if (!servicePointRepository.existsById(id)) {
            throw new EntityNotFoundException("ServicePoint not found with id: " + id);
        }
        servicePointRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServicePointDto> getServicePointsByStatus(String status) {
        return Arrays.stream(ServicePointStatus.values())
                .filter(e -> e.name().equalsIgnoreCase(status))
                .findFirst()
                .map(statusEnum -> servicePointRepository.findByServicePointStatus(statusEnum).stream()
                        .map(servicePointMapper::toDto)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }


}