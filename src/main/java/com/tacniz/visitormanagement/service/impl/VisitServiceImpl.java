package com.tacniz.visitormanagement.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.VisitDto;
import com.tacniz.visitormanagement.model.TimeRange;
import com.tacniz.visitormanagement.model.Visit;
import com.tacniz.visitormanagement.model.VisitOption;
import com.tacniz.visitormanagement.model.VisitRow;
import com.tacniz.visitormanagement.repo.TimeRangeRepository;
import com.tacniz.visitormanagement.repo.VisitOptionRepository;
import com.tacniz.visitormanagement.repo.VisitRepository;
import com.tacniz.visitormanagement.repo.VisitRowRepo;
import com.tacniz.visitormanagement.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final ObjectMapper objectMapper;
    private final VisitOptionRepository visitOptionRepository;
    private final VisitRowRepo visitRowRepo;
    private final TimeRangeRepository timeRangeRepository;

    @Override
    @Transactional
    public VisitDto createVisit(VisitDto visitDto) {
        // Convert and validate
        Visit visit = objectMapper.convertValue(visitDto, Visit.class);
        VisitOption visitOption = visitOptionRepository.findById(visit.getVisitOption().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid visit option ID"));
        visit.setVisitOption(visitOption);



        // Get or create visit rows
        List<VisitRow> visitRows = visitRowRepo.findByDateAndVisitOptionId(
                visit.getRequestedDate().toLocalDate(),
                visitOption.getId()
        );

        if(visitRows.isEmpty()) {
            visitRows = createVisitRowsForDate(visit.getRequestedDate().toLocalDate(), visitOption);
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

        // Save dynamic answers
        Visit savedVisit = null;
        visit.getDynamicAnswers().forEach(answer -> answer.setVisit(visit));
        if (visitRepository.existsByVisitRowAndVisitor(
                availableRow,
                visit.getVisitor())) {
            throw new IllegalArgumentException(
                    "Visitor already has a booking in this time slot");
        }else {
            savedVisit = visitRepository.save(visit);
        }

        return objectMapper.convertValue(savedVisit,VisitDto.class);
    }

    private List<VisitRow> createVisitRowsForDate(LocalDate date, VisitOption visitOption) {
        List<VisitRow> newVisitRows = new ArrayList<>();
        int averageTime = visitOption.getAverageTimeForAPerson();

        for(TimeRange timeRange : visitOption.getTimeRanges()) {
            LocalTime startTime = timeRange.getStartTime();
            LocalTime endTime = timeRange.getEndTime();

            while(startTime.plusMinutes(averageTime).isBefore(endTime) ||
                    startTime.plusMinutes(averageTime).equals(endTime)) {

                LocalTime slotEndTime = startTime.plusMinutes(averageTime);

                VisitRow visitRow = VisitRow.builder()
                        .visitorsPerRow(visitOption.getVisitorsPerRow())
                        .startTime(startTime)
                        .endTime(slotEndTime)
                        .visitOption(visitOption)
                        .date(date)
                        .visits(new ArrayList<>())
                        .timeRange(timeRange)
                        .build();

                newVisitRows.add(visitRow);
                startTime = slotEndTime;
            }
        }

        return visitRowRepo.saveAll(newVisitRows);
    }
    private Visit getTodaysLastVisit(){
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay(); // 00:00:00
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX); // 23:59:59.999999999


        return visitRepository.findTopByPrintedDateBetweenOrderByPrintedDateDesc(startOfDay, endOfDay)
                    .orElseThrow(() -> new IllegalArgumentException("No visits found for today"));

    }

    @Override
    public VisitDto getVisitById(Long id) {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Visit not found with id: " + id));
        return objectMapper.convertValue(visit, VisitDto.class);
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

    private List<Visit> getByVisitOptionId(Long id){
        return visitRepository.findByVisitOptionId(id);
    }
}