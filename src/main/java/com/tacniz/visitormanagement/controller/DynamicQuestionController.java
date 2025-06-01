package com.tacniz.visitormanagement.controller;

import com.tacniz.visitormanagement.dto.DynamicQuestionDTO;
import com.tacniz.visitormanagement.model.DynamicQuestion;
import com.tacniz.visitormanagement.service.DynamicQuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class DynamicQuestionController {

    @Autowired
    private DynamicQuestionService service;

    @GetMapping("/visitOption/{visitOptionId}")
    public ResponseEntity<List<DynamicQuestionDTO>> getQuestionsByVisitOptionId(@PathVariable Long visitOptionId) {
        List<DynamicQuestionDTO> questions = service.getQuestionsByVisitOptionId(visitOptionId);
        return ResponseEntity.ok(questions);
    }

    @PostMapping("/create")
    public ResponseEntity<DynamicQuestionDTO> createQuestion(@RequestBody DynamicQuestionDTO question) {
        DynamicQuestionDTO savedQuestion = service.saveQuestion(question);
        return ResponseEntity.ok(savedQuestion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DynamicQuestionDTO> getQuestionById(@PathVariable Long id) {
        DynamicQuestionDTO question = service.getQuestionById(id);
        return ResponseEntity.ok(question);
    }
    @GetMapping("/all")
    public ResponseEntity<List<DynamicQuestionDTO>> getQuestionAll() {

        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/update")
    public ResponseEntity<DynamicQuestionDTO> updateQuestion(@RequestBody @Valid DynamicQuestionDTO updatedQuestion) {
        DynamicQuestionDTO question = service.updateQuestion(updatedQuestion);
        return ResponseEntity.ok(question);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        service.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

}
