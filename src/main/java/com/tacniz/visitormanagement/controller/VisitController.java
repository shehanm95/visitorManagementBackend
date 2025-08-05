package com.tacniz.visitormanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.FullVisitDto;
import com.tacniz.visitormanagement.dto.VisitDto;
import com.tacniz.visitormanagement.dto.VisitRowDto;
import com.tacniz.visitormanagement.dto.VisitRowReq;
import com.tacniz.visitormanagement.model.VisitOption;
import com.tacniz.visitormanagement.service.VisitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;
    private final ObjectMapper objectMapper;


    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<VisitDto> createVisit(
        @RequestPart("visitDto") String visitDtoJson,
        @RequestPart(value = "image" , required = false) MultipartFile image) throws IOException {
        VisitDto visitDto = objectMapper.readValue(visitDtoJson, VisitDto.class);
        visitDto.setImage(image);
        VisitDto createdVisit = visitService.createVisit(visitDto);

        return new ResponseEntity<>(createdVisit, HttpStatus.CREATED);
    }



    @GetMapping("/{id}")
    public ResponseEntity<FullVisitDto> getVisitById(@PathVariable Long id) {
        FullVisitDto visitDto = visitService.getVisitById(id);
        return ResponseEntity.ok(visitDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VisitDto> updateVisit(@PathVariable Long id, @RequestBody VisitDto visitDto) {
        VisitDto updatedVisit = visitService.updateVisit(id, visitDto);
        return ResponseEntity.ok(updatedVisit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisit(@PathVariable Long id) {
        visitService.deleteVisit(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-visit-option/{visitOptionId}")
    public ResponseEntity<List<VisitDto>> getVisitsByVisitOptionId(@PathVariable Long visitOptionId) {
        List<VisitDto> visits = visitService.getVisitsByVisitOptionId(visitOptionId);
        return ResponseEntity.ok(visits);
    }

    @GetMapping("/by-visitor/{visitorUserId}")
    public ResponseEntity<List<VisitDto>> getVisitsByVisitorUserId(@PathVariable Long visitorUserId) {
        List<VisitDto> visits = visitService.getVisitsByVisitorUserId(visitorUserId);
        return ResponseEntity.ok(visits);
    }

    @GetMapping("/all")
    public  ResponseEntity<List<VisitDto>> getAll(){
        return ResponseEntity.ok(visitService.getAll());
    }

    @GetMapping("/getByRowId/{id}")
    public ResponseEntity<List<VisitDto>> getByRowId(@PathVariable Long id){
        return ResponseEntity.ok(visitService.getByRowId(id));
    }

    @PutMapping("/markAsPrinted/{id}")
    public ResponseEntity<?> markAsPrinted(@PathVariable Long id){
       if(id == null){
           throw new IllegalArgumentException("to mark as print visit id must not be null");
       }
       visitService.markAsPrinted(id);
     return ResponseEntity.ok(null);
    }

    @PostMapping("/getVisitRowsForDate")
    public List<VisitRowDto> getVisitRowsForDate(@RequestBody @Valid VisitRowReq visitRowReq){
        return visitService.getVisitRowsForDate(visitRowReq.getDate(),visitRowReq.getVisitOption());
    }

    @PostMapping("/createPreRegVisit")
    public ResponseEntity<VisitDto>  cratePreReg(@RequestBody VisitDto visit){
        VisitDto createdVisit = visitService.createPreReg(visit);
        return ResponseEntity.ok(createdVisit);
    }


    @GetMapping("/pic/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName){
        return visitService.getImage(imageName);
    }
}