package com.tacniz.visitormanagement.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.WorkingDayDto;
import com.tacniz.visitormanagement.model.WorkingDay;
import com.tacniz.visitormanagement.repo.WorkingDaysRepo;
import com.tacniz.visitormanagement.service.WorkingDayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkingDayServiceImpl implements WorkingDayService {

    private final WorkingDaysRepo workingDaysRepo;
    private final ObjectMapper objectMapper;


    @Override
    public List<WorkingDayDto> getAll() {
        if (workingDaysRepo.count() == 0) {
            List<WorkingDay> workingDays = Arrays.asList(
                    WorkingDay.builder().dateName("Monday").isWorking(true).build(),
                    WorkingDay.builder().dateName("Tuesday").isWorking(true).build(),
                    WorkingDay.builder().dateName("Wednesday").isWorking(true).build(),
                    WorkingDay.builder().dateName("Thursday").isWorking(true).build(),
                    WorkingDay.builder().dateName("Friday").isWorking(true).build(),
                    WorkingDay.builder().dateName("Saturday").isWorking(false).build(),
                    WorkingDay.builder().dateName("Sunday").isWorking(false).build()
            );
            workingDaysRepo.saveAll(workingDays);
        }

        return workingDaysRepo.findAll().stream()
                .map(d-> objectMapper.convertValue(d,WorkingDayDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkingDayDto> setAll(List<WorkingDayDto> workingDays) {
        List<WorkingDay> wd = workingDays
                .stream()
                .map( w-> objectMapper.convertValue(w,WorkingDay.class))
                .toList();
        return workingDaysRepo.saveAll(wd).stream()
                .map(w->objectMapper.convertValue(w,WorkingDayDto.class))
                .collect(Collectors.toList());
    }


}
