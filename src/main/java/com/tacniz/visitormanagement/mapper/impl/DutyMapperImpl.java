package com.tacniz.visitormanagement.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.DutyDto;
import com.tacniz.visitormanagement.dto.UserDto;
import com.tacniz.visitormanagement.model.Duty;
import com.tacniz.visitormanagement.mapper.DutyMapper;
import com.tacniz.visitormanagement.mapper.ServicePointMapper;
import com.tacniz.visitormanagement.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DutyMapperImpl implements DutyMapper {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    @Lazy
    private ServicePointMapper servicePointMapper;




    @Override
    public DutyDto toDto(Duty duty) {
        if (duty == null) return null;

        DutyDto dto = objectMapper.convertValue(duty, DutyDto.class);

       // dto.setServicePoint(servicePointMapper.toDto(duty.getServicePoint()));
        dto.setOfficer(objectMapper.convertValue(duty.getOfficer(), UserDto.class));

        return dto;
    }

    @Override
    public Duty toEntity(DutyDto dto) {
        if (dto == null) return null;

        Duty duty = objectMapper.convertValue(dto, Duty.class);

        duty.setServicePoint(servicePointMapper.toEntity(dto.getServicePoint()));
        duty.setOfficer(objectMapper.convertValue(dto.getOfficer(), UserEntity.class));

        return duty;
    }

    @Override
    public List<DutyDto> toDtoList(List<Duty> duties) {
        if (duties == null) return null;
        return duties.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<Duty> toEntityList(List<DutyDto> dutyDtos) {
        if (dutyDtos == null) return null;
        return dutyDtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
