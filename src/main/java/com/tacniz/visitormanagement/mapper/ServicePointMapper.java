package com.tacniz.visitormanagement.mapper;


import com.tacniz.visitormanagement.dto.ServicePointDto;
import com.tacniz.visitormanagement.model.ServicePoint;

import java.util.List;

public interface ServicePointMapper {

    ServicePointDto toDto(ServicePoint servicePoint);

    ServicePoint toEntity(ServicePointDto dto);

    List<ServicePointDto> toDtoList(List<ServicePoint> servicePoints);

    List<ServicePoint> toEntityList(List<ServicePointDto> dtos);
}
