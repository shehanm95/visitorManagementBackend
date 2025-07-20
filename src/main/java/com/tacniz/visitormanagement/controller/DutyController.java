package com.tacniz.visitormanagement.controller;

import com.tacniz.visitormanagement.dto.DutyDto;
import com.tacniz.visitormanagement.model.Duty;
import com.tacniz.visitormanagement.repo.DutyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/duty")
@RequiredArgsConstructor
public class DutyController {

    private final DutyRepository dutyRepository;

    @PostMapping("/add")
    public Duty save (@RequestBody Duty duty){
        return dutyRepository.save(duty);
    }

}
