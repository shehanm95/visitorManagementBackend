package com.tacniz.visitormanagement.service.impl;

import com.tacniz.visitormanagement.dto.WorkingDaysDto;
import com.tacniz.visitormanagement.repo.WorkingDaysRepo;
import com.tacniz.visitormanagement.service.WorkingDaysService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkingDaysServiceImpl implements WorkingDaysService {

    private final WorkingDaysRepo workingDaysRepo;
    @Override
    public List<WorkingDaysDto> getAll() {
        if(workingDaysRepo.count() == 0){
            
        }
        return
    }
}
