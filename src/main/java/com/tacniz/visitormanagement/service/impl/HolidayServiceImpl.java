package com.tacniz.visitormanagement.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacniz.visitormanagement.dto.HolidayDto;
import com.tacniz.visitormanagement.dto.VisitOptionDTO;
import com.tacniz.visitormanagement.model.Holiday;
import com.tacniz.visitormanagement.repo.HolidayRepo;
import com.tacniz.visitormanagement.repo.VisitOptionRepository;
import com.tacniz.visitormanagement.service.HolidayService;
import com.tacniz.visitormanagement.service.VisitOptionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HolidayServiceImpl implements HolidayService {

    private final HolidayRepo holidayRepo;
    private final VisitOptionService visitOptionService;
    private final ObjectMapper objectMapper;

    @Autowired
    private VisitOptionRepository visitOptionRepository;

    @Override
    public List<HolidayDto> getAllHolidays() {
        return holidayRepo.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<HolidayDto> getAllFutureHolidays() {
        return holidayRepo.findByDateAfter(LocalDate.now()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<HolidayDto> getHolidaysByVisitOption(Long visitOptionId) {
        return holidayRepo.findByVisitOptionId(visitOptionId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<HolidayDto> getFutureHolidaysByVisitOption(Long visitOptionId) {
        return holidayRepo.findByVisitOptionIdAndDateAfter(visitOptionId, LocalDate.now()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public HolidayDto createHoliday(HolidayDto holidayDto) {
        Holiday holiday = convertToEntity(holidayDto);
        Holiday savedHoliday = holidayRepo.save(holiday);
        return convertToDto(savedHoliday);
    }

    @Override
    @Transactional
    public HolidayDto updateHoliday(Long id, HolidayDto holidayDto) {
        Holiday existingHoliday = holidayRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Holiday not found with id: " + id));

        Holiday updatedHoliday = convertToEntity(holidayDto);
        updatedHoliday.setId(existingHoliday.getId());

        Holiday savedHoliday = holidayRepo.save(updatedHoliday);
        return convertToDto(savedHoliday);
    }

    @Override
    @Transactional
    public void deleteHoliday(Long id) {
        holidayRepo.deleteById(id);
    }

    @Override
    public List<HolidayDto> saveAll( List<HolidayDto> holidays) {
        List<Holiday> Hdays = holidays
                .stream()
                .map(h->objectMapper.convertValue(h,Holiday.class))
                .toList();
        return holidayRepo.saveAll(Hdays).stream()
                .map(h->objectMapper.convertValue(h,HolidayDto.class))
                .toList();
    }

    private HolidayDto convertToDto(Holiday holiday) {
        HolidayDto dto = objectMapper.convertValue(holiday, HolidayDto.class);
        if (holiday.getVisitOption() != null) {
            dto.setVisitOption(objectMapper.convertValue(
                    holiday.getVisitOption(), VisitOptionDTO.class));
        }
        return dto;
    }

    private Holiday convertToEntity(HolidayDto dto) {
        Holiday holiday = objectMapper.convertValue(dto, Holiday.class);
        if (dto.getVisitOption() != null) {
            holiday.setVisitOption(visitOptionRepository.findById(
                    dto.getVisitOption().getId()).orElseThrow(()-> new IllegalArgumentException("visitOption not found by id (in holidays)")));
        }
        return holiday;
    }
}