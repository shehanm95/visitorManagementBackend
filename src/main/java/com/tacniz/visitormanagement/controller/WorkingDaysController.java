package com.tacniz.visitormanagement.controller;


import com.tacniz.visitormanagement.dto.WorkingDayDto;
import com.tacniz.visitormanagement.model.WorkingDay;
import com.tacniz.visitormanagement.service.WorkingDayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/workingDays")
@RestController
public class WorkingDaysController {

    private final WorkingDayService workingDayService;

    @GetMapping("/getAll")
    public ResponseEntity<List<WorkingDayDto>> getAll(){
        return ResponseEntity.ok(workingDayService.getAll());
    }

    @PostMapping("/serAll")
    public ResponseEntity<List<WorkingDayDto>> setAll(@RequestBody List<WorkingDayDto> workingDays){
        return ResponseEntity.ok(workingDayService.setAll(workingDays));
    }
}
