package com.tacniz.visitormanagement.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.SpecificDateDto;
import com.tacniz.visitormanagement.model.SpecificDate;
import com.tacniz.visitormanagement.repo.SpecificDateRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/specificDates")
@RequiredArgsConstructor
public class SpecificDateController {

    private final SpecificDateRepo specificDateRepo;
    private final ObjectMapper objectMapper;
    @GetMapping("/all")
    public ResponseEntity<List<SpecificDateDto>> getAll(){
        return ResponseEntity.ok(specificDateRepo
                .findAll()
                .stream()
                .map(d-> objectMapper
                        .convertValue(d,SpecificDateDto.class))
                .toList());

    }
    @GetMapping("/byOption/{id}")
    public ResponseEntity<List<SpecificDateDto>> byOption(@PathVariable Long id){
        return ResponseEntity.ok(specificDateRepo
                .findAllByVisitOptionId(id)
                .stream()
                .map(d-> objectMapper
                        .convertValue(d,SpecificDateDto.class))
                .toList());

    }
}
