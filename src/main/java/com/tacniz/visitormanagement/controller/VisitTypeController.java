package com.tacniz.visitormanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.VisitTypeDTO;
import com.tacniz.visitormanagement.service.ImageService;
import com.tacniz.visitormanagement.service.VisitTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/api/visit-types")
@RequiredArgsConstructor
public class VisitTypeController {

    private final VisitTypeService visitTypeService;
    private final ObjectMapper objectMapper;
    private final ImageService imageService;

    // Create
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<VisitTypeDTO> createVisitType(
            @RequestPart("visitType") String visitTypeJson,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        // Manually deserialize the visitType JSON string to VisitTypeDTO
        VisitTypeDTO visitTypeDTO = objectMapper.readValue(visitTypeJson, VisitTypeDTO.class);
        visitTypeDTO.setImage(image);
        VisitTypeDTO createdVisitType = visitTypeService.createVisitType(visitTypeDTO);
        return new ResponseEntity<>(createdVisitType, HttpStatus.CREATED);
    }


    // Read (Get by ID)
    @GetMapping("/{id}")
    public ResponseEntity<VisitTypeDTO> getVisitTypeById(@PathVariable Long id) {
        VisitTypeDTO visitTypeDTO = visitTypeService.getVisitTypeById(id);
        return ResponseEntity.ok(visitTypeDTO);
    }

    // Read (Get all)
    @GetMapping("/all")
    public ResponseEntity<List<VisitTypeDTO>> getAllVisitTypes() {
        List<VisitTypeDTO> visitTypes = visitTypeService.getAllVisitTypes();
        return ResponseEntity.ok(visitTypes);
    }


    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<VisitTypeDTO> updateVisitType(
            @RequestPart("visitType") String visitTypeJson,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        // Manually deserialize the visitType JSON string to VisitTypeDTO
        VisitTypeDTO visitTypeDTO = objectMapper.readValue(visitTypeJson, VisitTypeDTO.class);
        visitTypeDTO.setImage(image);
        VisitTypeDTO createdVisitType = visitTypeService.updateVisitType(visitTypeDTO);
        return new ResponseEntity<>(createdVisitType, HttpStatus.CREATED);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisitType(@PathVariable Long id) {
        visitTypeService.deleteVisitType(id);
        return ResponseEntity.noContent().build();
    }

    // Get Image
    @GetMapping("/cover/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws MalformedURLException {
        return visitTypeService.getImage(filename);
    }


    @DeleteMapping("/cover/delete/{filename}")
    public ResponseEntity<VisitTypeDTO> dltImage(@PathVariable String filename) throws MalformedURLException {
       return ResponseEntity.ok(objectMapper.convertValue(visitTypeService.deleteCover(filename), VisitTypeDTO.class));
    }

    private String determineContentType(String filename) {
        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        return switch (extension) {
            case "png" -> "image/png";
            case "jpg", "jpeg" -> "image/jpeg";
            case "gif" -> "image/gif";
            default -> "application/octet-stream";
        };
    }
}