package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.itis.vhsroni.dto.response.CalendarificApiResponse;
import ru.itis.vhsroni.dto.response.HolidayResponse;
import ru.itis.vhsroni.exceptions.HolidayServiceException;
import ru.itis.vhsroni.services.impl.BaseHolidayServiceImpl;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BaseHolidayServiceImplTest {

    private static final String TEST_API_KEY = "test-api-key";

    private static final String TEST_API_URL = "http://test-api-url";

    private static final String TEST_COUNTRY = "RU";

    private static final int TEST_YEAR = 2023;

    private static final String HOLIDAY_DATE_1 = "2023-01-01";

    private static final String HOLIDAY_NAME_1 = "New Year";

    private static final String HOLIDAY_DESC_1 = "Celebration";

    private static final String INVALID_DATE = "invalid-date";

    private static final String INVALID_HOLIDAY_NAME = "Invalid";

    private static final String HOLIDAY_NAME_CURRENT = "Current Month Holiday";

    private static final String HOLIDAY_NAME_NEXT = "Next Month Holiday";

    private static final int SUCCESS_CODE = 200;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private BaseHolidayServiceImpl holidayService;

    @BeforeEach
    void BeforeEach() throws Exception {
        holidayService = new BaseHolidayServiceImpl(restTemplate);
        setPrivateField(holidayService, "calendarificApiKey", TEST_API_KEY);
        setPrivateField(holidayService, "calendarificUrl", TEST_API_URL);
    }

    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    @Test
    void testGetHolidaysForYearSuccess() {
        CalendarificApiResponse.Holiday testHoliday = createTestHoliday(HOLIDAY_DATE_1, HOLIDAY_NAME_1, HOLIDAY_DESC_1);
        CalendarificApiResponse apiResponse = createApiResponse(List.of(testHoliday), SUCCESS_CODE);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(CalendarificApiResponse.class)))
                .thenReturn(new ResponseEntity<>(apiResponse, HttpStatus.OK));

        List<HolidayResponse> result = holidayService.getHolidaysForYear(TEST_COUNTRY, TEST_YEAR);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(HOLIDAY_DATE_1, result.get(0).getDate());
        assertEquals(HOLIDAY_NAME_1, result.get(0).getName());
        assertEquals(HOLIDAY_DESC_1, result.get(0).getDescription());
    }

    @Test
    void testGetHolidaysForYearApiErrorStatus() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(CalendarificApiResponse.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_GATEWAY));
        HolidayServiceException exception = assertThrows(
                HolidayServiceException.class,
                () -> holidayService.getHolidaysForYear(TEST_COUNTRY, TEST_YEAR)
        );
        assertNotNull(exception.getErrorMessage());
        assertTrue(exception.getErrorMessage().contains("API request failed"));
    }

    @Test
    void tetsGetHolidaysForYearEmptyResponseBody() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(CalendarificApiResponse.class)))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.OK));
        HolidayServiceException exception = assertThrows(
                HolidayServiceException.class,
                () -> holidayService.getHolidaysForYear(TEST_COUNTRY, TEST_YEAR)
        );
        assertNotNull(exception.getErrorMessage());
        assertTrue(exception.getErrorMessage().contains("Empty response body"));
    }

    @Test
    void testGetHolidaysForYearNoHolidaysFound() {
        CalendarificApiResponse apiResponse = createApiResponse(null, SUCCESS_CODE);
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(CalendarificApiResponse.class))
        ).thenReturn(new ResponseEntity<>(apiResponse, HttpStatus.OK));
        List<HolidayResponse> result = holidayService.getHolidaysForYear(TEST_COUNTRY, TEST_YEAR);
        assertNotNull(result);
        assertTrue(result.isEmpty(), "Expected empty list when no holidays found");
    }


    @Test
    void testGetHolidaysForCurrentMonthSuccess() {
        LocalDate now = LocalDate.now();
        String currentMonthDate = now.withDayOfMonth(1).toString();
        String nextMonthDate = now.plusMonths(1).withDayOfMonth(1).toString();
        CalendarificApiResponse.Holiday currentHoliday = createTestHoliday(currentMonthDate, HOLIDAY_NAME_CURRENT, "");
        CalendarificApiResponse.Holiday nextHoliday = createTestHoliday(nextMonthDate, HOLIDAY_NAME_NEXT, "");
        CalendarificApiResponse apiResponse = createApiResponse(List.of(currentHoliday, nextHoliday), SUCCESS_CODE);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(CalendarificApiResponse.class)))
                .thenReturn(new ResponseEntity<>(apiResponse, HttpStatus.OK));
        List<HolidayResponse> result = holidayService.getHolidaysForCurrentMonth(TEST_COUNTRY);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(currentMonthDate, result.get(0).getDate());
        assertEquals(HOLIDAY_NAME_CURRENT, result.get(0).getName());
    }

    @Test
    void testGetHolidaysForCurrentMonthInvalidDateFormat() {
        CalendarificApiResponse.Holiday invalidHoliday = createTestHoliday(INVALID_DATE, INVALID_HOLIDAY_NAME, "");
        CalendarificApiResponse apiResponse = createApiResponse(Collections.singletonList(invalidHoliday), SUCCESS_CODE);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(CalendarificApiResponse.class)))
                .thenReturn(new ResponseEntity<>(apiResponse, HttpStatus.OK));

        HolidayServiceException exception = assertThrows(
                HolidayServiceException.class,
                () -> holidayService.getHolidaysForCurrentMonth(TEST_COUNTRY)
        );
        assertNotNull(exception.getErrorMessage());
        assertTrue(exception.getErrorMessage().contains("Invalid date format"));
    }

    private CalendarificApiResponse createApiResponse(List<CalendarificApiResponse.Holiday> holidays, int code) {
        CalendarificApiResponse apiResponse = new CalendarificApiResponse();
        CalendarificApiResponse.Meta meta = new CalendarificApiResponse.Meta();
        meta.setCode(code);
        apiResponse.setMeta(meta);
        CalendarificApiResponse.Response response = new CalendarificApiResponse.Response();
        response.setHolidays(holidays);
        apiResponse.setResponse(response);
        return apiResponse;
    }

    private CalendarificApiResponse.Holiday createTestHoliday(String date, String name, String description) {
        CalendarificApiResponse.Holiday holiday = new CalendarificApiResponse.Holiday();
        holiday.setName(name);
        holiday.setDescription(description);
        CalendarificApiResponse.DateObj holidayDate = new CalendarificApiResponse.DateObj();
        holidayDate.setIso(date);
        holiday.setDate(holidayDate);
        return holiday;
    }
}
