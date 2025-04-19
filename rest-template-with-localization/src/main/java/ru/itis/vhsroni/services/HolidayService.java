package ru.itis.vhsroni.services;

import ru.itis.vhsroni.dto.response.HolidayResponse;

import java.util.List;

public interface HolidayService {

    List<HolidayResponse> getHolidaysForCurrentMonth(String country);

    List<HolidayResponse> getHolidaysForYear(String country, int year);
}
