package com.tacniz.visitormanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.VisitOptionDTO;
import com.tacniz.visitormanagement.mapper.VisitOptionMapper;
import com.tacniz.visitormanagement.service.VisitOptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/api/visit-options")
@RequiredArgsConstructor
public class VisitOptionController {

    private final VisitOptionService visitOptionService;
    private final ObjectMapper objectMapper;
    private final VisitOptionMapper visitOptionMapper;

    @PostMapping(path = "/add", consumes = "multipart/form-data")
    public ResponseEntity<VisitOptionDTO> createVisitOption(
            @Valid @RequestPart("visitOption") String visitOptionJson,
            @RequestPart(value = "image", required = false) MultipartFile coverImage) throws IOException {

        VisitOptionDTO visitOptionDTO = objectMapper.readValue(visitOptionJson, VisitOptionDTO.class);
        visitOptionDTO.setImage(coverImage);
        VisitOptionDTO createdVisitOption = visitOptionService.createVisitOption(visitOptionDTO);
        return new ResponseEntity<>(createdVisitOption, HttpStatus.CREATED);
    }

    // Read (Get by ID)
    @GetMapping("/get/{id}")
    public ResponseEntity<VisitOptionDTO> getVisitOptionById(@PathVariable Long id) {
        VisitOptionDTO visitOptionDTO = visitOptionService.getVisitOptionById(id);
        return ResponseEntity.ok(visitOptionDTO);
    }

    // Read (Get all)
    @GetMapping("/all")
    public ResponseEntity<List<VisitOptionDTO>> getAllVisitOptions() {
        List<VisitOptionDTO> visitOptions = visitOptionService.getAllVisitOptions();
        return ResponseEntity.ok(visitOptions);
    }

    @GetMapping("/cover/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) throws MalformedURLException {
        return visitOptionService.getImage(fileName);
    }

    @DeleteMapping("/delete/cover/{fileName}")
    public ResponseEntity<VisitOptionDTO> deleteCover(@PathVariable String fileName){
        return ResponseEntity.ok(visitOptionService.deleteCover(fileName));
    }


    // Read (Get by VisitType)
    @GetMapping("/by-visit-type/{visitTypeId}")
    public ResponseEntity<List<VisitOptionDTO>> getVisitOptionsByVisitType(@PathVariable Long visitTypeId) {
        List<VisitOptionDTO> visitOptions = visitOptionService.getVisitOptionsByVisitType(visitTypeId);
        return ResponseEntity.ok(visitOptions);
    }

    // Update
    @PutMapping(value = "/update", consumes = "multipart/form-data")
    public ResponseEntity<VisitOptionDTO> update(
            @Valid @RequestPart("visitOption") String visitOptionJson,
            @RequestPart(value = "image", required = false) MultipartFile coverImage) throws IOException {

        VisitOptionDTO visitOptionDTO = objectMapper.readValue(visitOptionJson, VisitOptionDTO.class);
        visitOptionDTO.setImage(coverImage);
        VisitOptionDTO createdVisitOption = visitOptionService.updateVisitOption(visitOptionDTO);
        return new ResponseEntity<>(createdVisitOption, HttpStatus.CREATED);
    }

    // Delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteVisitOption(@PathVariable Long id) {
        visitOptionService.deleteVisitOption(id);
        return ResponseEntity.noContent().build();
    }
}