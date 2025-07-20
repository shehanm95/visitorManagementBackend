package com.tacniz.visitormanagement.service;



import com.tacniz.visitormanagement.dto.ServicePointDto;

import java.util.List;

public interface ServicePointService {
    ServicePointDto createServicePoint(ServicePointDto servicePointDto);
    ServicePointDto getServicePointById(Long id);
    List<ServicePointDto> getAllServicePoints();
    ServicePointDto updateServicePoint(Long id, ServicePointDto servicePointDto);
    void deleteServicePoint(Long id);
    List<ServicePointDto> getServicePointsByStatus(String status) throws IllegalArgumentException;
}