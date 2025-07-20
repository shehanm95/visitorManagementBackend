package com.tacniz.visitormanagement.service;

import com.tacniz.visitormanagement.dto.HolidayDto;

import java.util.List;

public interface HolidayService {
    List<HolidayDto> getAllHolidays();
    List<HolidayDto> getAllFutureHolidays();
    List<HolidayDto> getHolidaysByVisitOption(Long visitOptionId);
    List<HolidayDto> getFutureHolidaysByVisitOption(Long visitOptionId);
    HolidayDto createHoliday(HolidayDto holidayDto);
    HolidayDto updateHoliday(Long id, HolidayDto holidayDto);
    void deleteHoliday(Long id);

    List<HolidayDto> saveAll( List<HolidayDto> holidays);
}
