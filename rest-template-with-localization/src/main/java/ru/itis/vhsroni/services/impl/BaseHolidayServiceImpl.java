package ru.itis.vhsroni.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.itis.vhsroni.dto.response.CalendarificApiResponse;
import ru.itis.vhsroni.dto.response.HolidayResponse;
import ru.itis.vhsroni.exceptions.HolidayServiceException;
import ru.itis.vhsroni.services.HolidayService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BaseHolidayServiceImpl implements HolidayService {

    private final RestTemplate restTemplate;

    @Value("${calendarific.api.key}")
    private String calendarificApiKey;

    @Value("${calendarific.api.url}")
    private String calendarificUrl;

    @Override
    public List<HolidayResponse> getHolidaysForYear(String country, int year) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(calendarificUrl)
                .queryParam("api_key", calendarificApiKey)
                .queryParam("country", country)
                .queryParam("year", year);

        try {
            ResponseEntity<CalendarificApiResponse> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    CalendarificApiResponse.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new HolidayServiceException("API request failed with status: " + response.getStatusCode());
            }

            if (response.getBody() == null) {
                throw new HolidayServiceException("Empty response body from API");
            }

            CalendarificApiResponse body = response.getBody();

            if (body.getMeta() != null && body.getMeta().getCode() != 200) {
                throw new HolidayServiceException("API returned error code: " + body.getMeta().getCode());
            }

            if (body.getResponse() == null || body.getResponse().getHolidays() == null) {
                return List.of();
            }

            return body.getResponse().getHolidays().stream()
                    .filter(h -> h.getDate() != null && h.getDate().getIso() != null)
                    .map(h -> new HolidayResponse(h.getDate().getIso(), h.getName(), h.getDescription()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            if (e instanceof HolidayServiceException) {
                throw (HolidayServiceException) e;
            }
            throw new HolidayServiceException("Error while fetching holidays: " + e.getMessage());
        }
    }

    @Override
    public List<HolidayResponse> getHolidaysForCurrentMonth(String country) {
        try {
            LocalDate today = LocalDate.now();
            int currentYear = today.getYear();
            int currentMonth = today.getMonthValue();

            List<HolidayResponse> allHolidays = getHolidaysForYear(country, currentYear);

            return allHolidays.stream()
                    .filter(holiday -> {
                        try {
                            LocalDate holidayDate = LocalDate.parse(holiday.getDate());
                            return holidayDate.getMonthValue() == currentMonth;
                        } catch (Exception e) {
                            throw new HolidayServiceException("Invalid date format in holiday data: " + holiday.getDate());
                        }
                    })
                    .map(holiday -> new HolidayResponse(holiday.getDate(), holiday.getName(), holiday.getDescription()))
                    .collect(Collectors.toList());
        } catch (HolidayServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new HolidayServiceException("Error while processing holidays for current month: " + e.getMessage());
        }
    }
}