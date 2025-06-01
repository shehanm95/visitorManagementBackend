package com.tacniz.visitormanagement.controller;

package com.tacniz.visitormanagement.controller;

import com.tacniz.visitormanagement.model.DynamicQuestion;
import com.tacniz.visitormanagement.service.DynamicQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class DynamicQuestionController {

    @Autowired
    private DynamicQuestionService service;

    @GetMapping("/visitOption/{visitOptionId}")
    public ResponseEntity<List<DynamicQuestion>> getQuestionsByVisitOptionId(@PathVariable Integer visitOptionId) {
        List<DynamicQuestion> questions = service.getQuestionsByVisitOptionId(visitOptionId);
        return ResponseEntity.ok(questions);
    }

    @PostMapping
    public ResponseEntity<DynamicQuestion> createQuestion(@RequestBody DynamicQuestion question) {
        DynamicQuestion savedQuestion = service.saveQuestion(question);
        return ResponseEntity.ok(savedQuestion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DynamicQuestion> getQuestionById(@PathVariable Integer id) {
        DynamicQuestion question = service.getQuestionById(id);
        return ResponseEntity.ok(question);
    }
}
