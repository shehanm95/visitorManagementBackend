package com.tacniz.visitormanagement.service.impl;

import com.tacniz.visitormanagement.dto.TimeRangeDto;
import com.tacniz.visitormanagement.mapper.impl.TimeRangeMapper;
import com.tacniz.visitormanagement.model.TimeRange;
import com.tacniz.visitormanagement.model.VisitOption;
import com.tacniz.visitormanagement.repo.TimeRangeRepository;
import com.tacniz.visitormanagement.repo.VisitOptionRepository;
import com.tacniz.visitormanagement.service.TimeRangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class TimeRangeServiceImpl implements TimeRangeService {

    private final TimeRangeRepository timeRangeRepository;
    private final TimeRangeMapper timeRangeMapper;
    private final VisitOptionRepository visitOptionRepository;

    @Autowired
    public TimeRangeServiceImpl(TimeRangeRepository timeRangeRepository,
                                TimeRangeMapper timeRangeMapper,
                                VisitOptionRepository visitOptionRepository) {
        this.timeRangeRepository = timeRangeRepository;
        this.timeRangeMapper = timeRangeMapper;
        this.visitOptionRepository = visitOptionRepository;
    }

    @Override
    public TimeRangeDto createTimeRange(TimeRangeDto timeRangeDto) {
        validateTimeRange(timeRangeDto);
        TimeRange timeRange = timeRangeMapper.toEntity(timeRangeDto);
        TimeRange savedTimeRange = timeRangeRepository.save(timeRange);
        return timeRangeMapper.toDto(savedTimeRange);
    }


    @Override
    public TimeRangeDto getTimeRangeById(Long id) {
        return timeRangeRepository.findById(id)
                .map(timeRangeMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("TimeRange not found with id: " + id));
    }

    @Override
    public List<TimeRangeDto> getAllTimeRanges() {
        return timeRangeMapper.toDtoList(timeRangeRepository.findAll());
    }

    @Override
    public List<TimeRangeDto> getTimeRangesByVisitOptionId(Long visitOptionId) {
        if (!visitOptionRepository.existsById(visitOptionId)) {
            throw new IllegalArgumentException("VisitOption not found with id: " + visitOptionId);
        }
        return timeRangeMapper.toDtoList(timeRangeRepository.findByVisitOptionId(visitOptionId));
    }

    @Override
    public TimeRangeDto updateTimeRange(Long id, TimeRangeDto timeRangeDto) {
        validateTimeRange(timeRangeDto);
        TimeRange existingTimeRange = timeRangeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("TimeRange not found with id: " + id));

        updateEntityFromDto(existingTimeRange, timeRangeDto);
        TimeRange updatedTimeRange = timeRangeRepository.save(existingTimeRange);
        return timeRangeMapper.toDto(updatedTimeRange);
    }

    @Override
    public void deleteTimeRange(Long id) {
        if (!timeRangeRepository.existsById(id)) {
            throw new IllegalArgumentException("TimeRange not found with id: " + id);
        }
        timeRangeRepository.deleteById(id);
    }

    // Helper methods
    private void validateTimeRange(TimeRangeDto timeRangeDto) {
        if (timeRangeDto.getStartTime() == null || timeRangeDto.getEndTime() == null) {
            throw new IllegalArgumentException("Both start time and end time are required");
        }

        if (timeRangeDto.getStartTime().isAfter(timeRangeDto.getEndTime())) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        if (timeRangeDto.getVisitOption() == null || timeRangeDto.getVisitOption().getId() == null) {
            throw new IllegalArgumentException("VisitOption ID is required");
        }
    }

    private void updateEntityFromDto(TimeRange entity, TimeRangeDto dto) {
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());

        if (dto.getVisitOption() != null && !entity.getVisitOption().getId().equals(dto.getVisitOption().getId())) {
            VisitOption visitOption = visitOptionRepository.findById(dto.getVisitOption().getId())
                    .orElseThrow(() -> new IllegalArgumentException("VisitOption not found with id: " + dto.getVisitOption().getId()));
            entity.setVisitOption(visitOption);
        }
    }
}
