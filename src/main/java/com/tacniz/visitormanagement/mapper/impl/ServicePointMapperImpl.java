package com.tacniz.visitormanagement.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.ServicePointDto;
import com.tacniz.visitormanagement.model.*;
import com.tacniz.visitormanagement.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ServicePointMapperImpl implements ServicePointMapper {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private DutyMapper dutyMapper;
    @Autowired
    private VisitMapper visitMapper;
    @Autowired
    private DynamicQuestionMapper dynamicQuestionMapper;
    @Autowired
    private SpecialNoteMapper specialNoteMapper;
    @Autowired
    private VisitOptionMapper visitOptionMapper;



    @Override
    public ServicePointDto toDto(ServicePoint servicePoint) {
        if (servicePoint == null) return null;

        ServicePointDto dto = objectMapper.convertValue(servicePoint, ServicePointDto.class);

        dto.setVisitOption(visitOptionMapper.toIdObject(servicePoint.getVisitOption()));
        dto.setDuties(dutyMapper.toDtoList(servicePoint.getDuties()));
        dto.setOfficerQuestions(dynamicQuestionMapper.toDtoList(servicePoint.getOfficerQuestions()));
        dto.setSpecialNotes(specialNoteMapper.toDtoList(servicePoint.getSpecialNotes()));

        return dto;
    }

    @Override
    public ServicePoint toEntity(ServicePointDto dto) {
        if (dto == null) return null;

        ServicePoint servicePoint = objectMapper.convertValue(dto, ServicePoint.class);

        servicePoint.setVisitOption(objectMapper.convertValue(dto.getVisitOption(), VisitOption.class));

        if (dto.getDuties() != null) {
            servicePoint.setDuties(dto.getDuties().stream()
                    .map(d -> {
                        var duty = dutyMapper.toEntity(d);
                        duty.setServicePoint(servicePoint);
                        return duty;
                    }).collect(Collectors.toList()));
        }

        if (dto.getOfficerQuestions() != null) {
            servicePoint.setOfficerQuestions(dto.getOfficerQuestions().stream()
                    .map(q -> {
                        var question = dynamicQuestionMapper.toEntity(q);
                        question.setServicePoint(servicePoint);
                        return question;
                    }).collect(Collectors.toList()));
        }

        if (dto.getSpecialNotes() != null) {
            servicePoint.setSpecialNotes(dto.getSpecialNotes().stream()
                    .map(sn -> {
                        var note = specialNoteMapper.toEntity(sn);
                        note.setServicePoint(servicePoint);
                        return note;
                    }).collect(Collectors.toList()));
        }

        if (dto.getVisits() != null) {
            servicePoint.setVisits(dto.getVisits().stream()
                    .map(v -> {
                        var visit = visitMapper.toEntity(v);
                        visit.setServicePoint(servicePoint);
                        return visit;
                    }).collect(Collectors.toList()));
        }

        return servicePoint;
    }

    @Override
    public List<ServicePointDto> toDtoList(List<ServicePoint> servicePoints) {
        if (servicePoints == null) return null;
        return servicePoints.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ServicePoint> toEntityList(List<ServicePointDto> dtos) {
        if (dtos == null) return null;
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
