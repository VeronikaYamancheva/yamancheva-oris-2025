package ru.itis.vhsroni.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.vhsroni.dto.response.HolidayListResponse;
import ru.itis.vhsroni.dto.response.HolidayResponse;
import ru.itis.vhsroni.services.HolidayService;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/holidays", produces = MediaType.APPLICATION_JSON_VALUE)
public class HolidayController {

    private final MessageSource messageSource;

    private final HolidayService holidayService;

    @GetMapping("/{country}")
    public ResponseEntity<HolidayListResponse> getHolidaysForMonth(
            @PathVariable("country") String country,
            Locale locale) {

        int monthNumber = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();
        String monthName = messageSource.getMessage("month." + monthNumber, null, locale);
        String message = messageSource.getMessage("holidays.message.month", new Object[]{monthName, year}, locale);
        List<HolidayResponse> holidays = holidayService.getHolidaysForCurrentMonth(country);
        return ResponseEntity.ok(new HolidayListResponse(message, holidays));
    }

    @GetMapping("/{country}/{year}")
    public ResponseEntity<HolidayListResponse> getHolidaysForYear(
            @PathVariable("country") String country,
            @PathVariable("year") int year,
            Locale locale) {

        String message = messageSource.getMessage("holidays.message.year", new Object[]{year}, locale);
        List<HolidayResponse> holidays = holidayService.getHolidaysForYear(country, year);
        return ResponseEntity.ok(new HolidayListResponse(message, holidays));
    }
}