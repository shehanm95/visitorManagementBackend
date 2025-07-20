package com.tacniz.visitormanagement.controller;

import com.tacniz.visitormanagement.dto.DynamicAnswerDto;
import com.tacniz.visitormanagement.service.DynamicAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dynamicAnswers")
@RequiredArgsConstructor
public class DynamicAnswerController {

    private final DynamicAnswerService dynamicAnswerService;

    @PostMapping("/addBulk")
    public ResponseEntity<List<DynamicAnswerDto>> addBulk(@RequestBody List<DynamicAnswerDto> dynamicAnswers){
        List<DynamicAnswerDto> list = dynamicAnswerService.saveAll(dynamicAnswers);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/byVisitId/{visitId}")
    public ResponseEntity<List<DynamicAnswerDto>> getAnswersByVisitId(@PathVariable Long visitId) {
        return ResponseEntity.ok(dynamicAnswerService.getAnswersByVisitId(visitId));
    }
}
