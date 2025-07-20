package com.tacniz.visitormanagement.controller;

import com.tacniz.visitormanagement.model.ServicePointDto;
import com.tacniz.visitormanagement.service.ServicePointService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/service-points")
@RequiredArgsConstructor
public class ServicePointController {

    private final ServicePointService servicePointService;

    @PostMapping
    public ResponseEntity<ServicePointDto> createServicePoint(
            @Valid @RequestBody ServicePointDto servicePointDto) {
        ServicePointDto createdServicePoint = servicePointService.createServicePoint(servicePointDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdServicePoint.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdServicePoint);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicePointDto> getServicePointById(@PathVariable Long id) {
        return ResponseEntity.ok(servicePointService.getServicePointById(id));
    }

    @GetMapping
    public ResponseEntity<List<ServicePointDto>> getAllServicePoints() {
        return ResponseEntity.ok(servicePointService.getAllServicePoints());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicePointDto> updateServicePoint(
            @PathVariable Long id,
            @Valid @RequestBody ServicePointDto servicePointDto) {
        return ResponseEntity.ok(servicePointService.updateServicePoint(id, servicePointDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicePoint(@PathVariable Long id) {
        servicePointService.deleteServicePoint(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ServicePointDto>> getServicePointsByStatus(
            @PathVariable String status) {
        return ResponseEntity.ok(servicePointService.getServicePointsByStatus(status));
    }
}