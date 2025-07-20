package com.tacniz.visitormanagement.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.FullVisitDto;
import com.tacniz.visitormanagement.dto.IdObject;
import com.tacniz.visitormanagement.dto.VisitDto;
import com.tacniz.visitormanagement.dto.VisitRowDto;
import com.tacniz.visitormanagement.mapper.FullVisitMapper;
import com.tacniz.visitormanagement.mapper.VisitMapper;
import com.tacniz.visitormanagement.model.*;
import com.tacniz.visitormanagement.repo.*;
import com.tacniz.visitormanagement.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final ObjectMapper objectMapper;
    private final VisitOptionRepository visitOptionRepository;
    private final VisitRowRepo visitRowRepo;
    private final TimeRangeRepository timeRangeRepository;
    private final VisitMapper visitMapper;
    private final ButtonAnswerRepository buttonAnswerRepository;

//    @Override
//    @Transactional
//    public VisitDto createVisit(VisitDto visitDto) {
//        // Convert and validate
//        Visit visit = objectMapper.convertValue(visitDto, Visit.class);
//        VisitOption visitOption = visitOptionRepository.findById(visit.getVisitOption().getId())
//                .orElseThrow(() -> new IllegalArgumentException("Invalid visit option ID"));
//        visit.setVisitOption(visitOption);
//
//
//
//
//
//        // Save dynamic answers
//        Visit savedVisit = null;
//        visit.getDynamicAnswers().forEach(answer -> answer.setVisit(visit));
//        if (visitRepository.existsByVisitRowAndVisitor(
//                availableRow,
//                visit.getVisitor())) {
//            throw new IllegalArgumentException(
//                    "Visitor already has a booking in this time slot");
//        }else {
//            savedVisit = visitRepository.save(visit);
//        }
//
//        return objectMapper.convertValue(savedVisit,VisitDto.class);
//    }

    @Override
    @Transactional
    public VisitDto createVisit(VisitDto visitDto) {

        VisitOption visitOption = visitOptionRepository.findById(visitDto.getVisitOption().getId()).orElseThrow(()->new IllegalArgumentException("attached visit option not available in the database"));
        Visit visit = visitMapper.toEntity(visitDto);
        //visit.getDynamicAnswers().forEach(da->da.getSelectedButtonAnswers().forEach(ba->ba.addDynamicAnswer(da)));
        System.out.println("visit mapped");
        visit.getDynamicAnswers().forEach(da -> {
            List<ButtonAnswer> attachedAnswers = new ArrayList<>();
            for (ButtonAnswer ba : da.getSelectedButtonAnswers()) {
                ButtonAnswer attachedBa = buttonAnswerRepository.findById(ba.getId())
                        .orElseThrow(() -> new IllegalArgumentException("ButtonAnswer with id " + ba.getId() + " not found"));
                attachedBa.addDynamicAnswer(da);
                attachedAnswers.add(attachedBa);
            }
            da.setSelectedButtonAnswers(attachedAnswers);
        });
        visit.setVisitOption(visitOption);

        // Get or create visit rows
        List<VisitRow> visitRows = visitRowRepo.findByDateAndVisitOptionId(
                visit.getRequestedDate().toLocalDate(),
                visit.getVisitOption().getId()
        );

       // System.out.println(visitRows);
        if(visitRows.isEmpty()) {
            visitRows = createVisitRowsForDate(visit.getRequestedDate().toLocalDate(), visit.getVisitOption());
        }

        VisitRow availableRow = null;
        boolean allInPast = true;

        for (VisitRow row : visitRows) {
            // Check if the start time is still valid (in the future)
            if (!row.getStartTime().isAfter(visit.getRequestedDate().toLocalTime())) {
                continue; // Start time has already passed
            }

            allInPast = false; // At least one row has a valid time

            // Check if the row has space
            if (row.getVisits().size() < row.getVisitorsPerRow()) {
                availableRow = row;
                break;
            }
        }
        System.out.println("time created");

// Throw appropriate exceptions based on the checks
        if (availableRow == null) {
            if (allInPast) {
                throw new IllegalArgumentException("Do not have any space for today");
            } else {
                throw new IllegalArgumentException("All slots are full for requested date");
            }
        }

        // Add visit to row
        availableRow.getVisits().add(visit);
        visit.setVisitRow(availableRow);
        visit.setPrintedDate(null);
        visit = visitRepository.save(visit);
        return visitMapper.toDto(visit);
    }


    private List<VisitRow> createVisitRowsForDate(LocalDate date, VisitOption visitOption) {
        List<VisitRow> allSavedRows = new ArrayList<>();
        int averageTime = visitOption.getAverageTimeForAPerson();
        final int BATCH_SIZE = 50; // Process 50 at a time

        for(TimeRange timeRange : visitOption.getTimeRanges()) {
            List<VisitRow> batchRows = new ArrayList<>();

            int startMinutes = timeRange.getStartTime().getHour() * 60 + timeRange.getStartTime().getMinute();
            int endMinutes = timeRange.getEndTime().getHour() * 60 + timeRange.getEndTime().getMinute();
            int currentMinutes = startMinutes;

            while(currentMinutes + averageTime <= endMinutes) {
                LocalTime startTime = LocalTime.of(currentMinutes / 60, currentMinutes % 60);
                LocalTime endTime = LocalTime.of((currentMinutes + averageTime) / 60,
                        (currentMinutes + averageTime) % 60);

                VisitRow visitRow = VisitRow.builder()
                        .visitorsPerRow(visitOption.getVisitorsPerRow())
                        .startTime(startTime)
                        .endTime(endTime)
                        .visitOption(visitOption)
                        .date(date)
                        .visits(new ArrayList<>())
                        .timeRange(timeRange)
                        .build();

                batchRows.add(visitRow);

                // Save in batches to free memory
                if(batchRows.size() >= BATCH_SIZE) {
                    List<VisitRow> savedBatch = visitRowRepo.saveAll(batchRows);
                    allSavedRows.addAll(savedBatch);
                    batchRows.clear(); // Free memory immediately
                    System.gc(); // Suggest garbage collection
                }

                currentMinutes += averageTime;
            }

            // Save remaining batch
            if(!batchRows.isEmpty()) {
                List<VisitRow> savedBatch = visitRowRepo.saveAll(batchRows);
                allSavedRows.addAll(savedBatch);
            }
        }

        return allSavedRows;
    }



    private Visit getTodaysLastVisit(){
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay(); // 00:00:00
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX); // 23:59:59.999999999


        return visitRepository.findTopByPrintedDateBetweenOrderByPrintedDateDesc(startOfDay, endOfDay)
                    .orElseThrow(() -> new IllegalArgumentException("No visits found for today"));

    }

    public List<VisitRowDto> getVisitRowsForDate(LocalDate date, IdObject visitOptionIdObj){
        VisitOption visitOption = visitOptionRepository.findById(visitOptionIdObj.getId())
                .orElseThrow(()->new IllegalArgumentException("visit option not exist in the database"));

        if(visitOption.getTimeRanges() == null || visitOption.getTimeRanges().isEmpty())
            throw new IllegalArgumentException("In VisitOption, time ranges cannot be null or empty to get visit rows");

//        if(visitOption.getVisitDateType() == VisitDateType.SPECIFIC_DATES){
//            boolean includes = false;
//            for(SpecificDate d :visitOption.getSpecificDates()){
//                if(d.getDate() == date) {
//                    includes = true;
//                    break;
//                }
//            };
//            if(!includes) {
//                throw new IllegalArgumentException("visitDate must me include in the specific visit dates");
//            }
//        }

        List<VisitRow> visitRows = visitRowRepo.findByVisitOptionIdAndDate(visitOption.getId(),date);
        if(visitRows.isEmpty()){
            visitRows = createVisitRowsForDate(date,visitOption);
        }

        List<VisitRowDto> rows = visitRows.stream()
                .map(r-> objectMapper.convertValue(r,VisitRowDto.class))
                .collect(Collectors.toList());

        rows.forEach(r-> r.setVisits(
                visitRepository.findByVisitRowId(r.getId())
                        .stream()
                        .map(v->objectMapper.convertValue(v,VisitDto.class))
                        .collect(Collectors.toList())

        ));
        return rows;

    }

    @Override
    public FullVisitDto getVisitById(Long id) {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Visit not found with id: " + id));
        return visitMapper.toFullVisitDto(visit);

    }

    @Override
    @Transactional
    public VisitDto updateVisit(Long id, VisitDto visitDto) {
        Visit existingVisit = visitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Visit not found with id: " + id));

        // Manual mapping for update
        Visit visitToBeUpdate = objectMapper.convertValue(visitDto,Visit.class);

        Visit updatedVisit = visitRepository.save(existingVisit);
        return objectMapper.convertValue(updatedVisit, VisitDto.class);
    }

    @Override
    public void deleteVisit(Long id) {
        if (!visitRepository.existsById(id)) {
            throw new IllegalArgumentException("Visit not found with id: " + id);
        }
        visitRepository.deleteById(id);
    }

    @Override
    public List<VisitDto> getVisitsByVisitOptionId(Long visitOptionId) {
        return visitRepository.findByVisitOptionId(visitOptionId).stream()
                .map(visit -> objectMapper.convertValue(visit, VisitDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<VisitDto> getVisitsByVisitorUserId(Long visitorUserId) {
        return visitRepository.findByVisitorId(visitorUserId).stream()
                .map(visit -> objectMapper.convertValue(visit, VisitDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<VisitDto> getAll() {
        return visitRepository.findAll()
                .stream()
                .map(v->objectMapper.convertValue(v,VisitDto.class))
                .toList();
    }

    @Override
    public List<VisitDto> getByRowId(Long id) {
        return visitRepository.findByVisitRowId(id)
                .stream()
                .map(v-> objectMapper.convertValue(v,VisitDto.class)).toList();
    }

    @Override
    public void markAsPrinted(Long id) {
        Visit visit = visitRepository.findById(id).orElseThrow(()->new IllegalArgumentException("requested visit is not exist in the database"));
        visit.setPrinted(true);
        visitRepository.save(visit);
    }

    @Override
    public VisitDto createPreReg(VisitDto visit) {
        VisitRow visitRow = visitRowRepo.findById(visit.getVisitRow().getId()).orElseThrow(()->new IllegalArgumentException("VisitRow not exist in the database"));
        if(visitRow.getVisitorsPerRow() < visitRow.getVisits().size()){
            throw new IllegalArgumentException("Sorry The visit row just now filed before you");
        }
        Visit visitToBeCreate = objectMapper.convertValue(visit,Visit.class);
        visitRow.addVisit(visitToBeCreate);
        Visit createVisit = visitRepository.save(visitToBeCreate);
        visitRowRepo.save(visitRow);
        return objectMapper.convertValue(createVisit,VisitDto.class);
    }

    private List<Visit> getByVisitOptionId(Long id){
        return visitRepository.findByVisitOptionId(id);
    }
}