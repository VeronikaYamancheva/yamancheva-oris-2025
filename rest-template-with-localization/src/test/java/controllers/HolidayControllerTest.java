package controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import ru.itis.vhsroni.controllers.HolidayController;
import ru.itis.vhsroni.dto.response.HolidayListResponse;
import ru.itis.vhsroni.dto.response.HolidayResponse;
import ru.itis.vhsroni.services.HolidayService;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class HolidayControllerTest {

    private static final String COUNTRY_RU = "RU";

    private static final String COUNTRY_US = "US";

    private static final Locale LOCALE_EN = Locale.ENGLISH;

    private static final int CURRENT_MONTH = LocalDate.now().getMonthValue();

    private static final int CURRENT_YEAR = LocalDate.now().getYear();

    private static final String MONTH_NAME = "April";

    private static final String MONTH_MESSAGE = "Holidays in April 2025";

    private static final String YEAR_MESSAGE = "Holidays in 2025";

    private static final List<HolidayResponse> HOLIDAYS_FOR_MONTH = List.of(
            new HolidayResponse("2025-04-01", "Holiday 1", "Description 1"),
            new HolidayResponse("2025-04-15", "Holiday 2", "Description 2")
    );

    private static final List<HolidayResponse> HOLIDAYS_FOR_YEAR = List.of(
            new HolidayResponse("2025-01-01", "New Year", "First day of the year")
    );

    @Mock
    private MessageSource messageSource;

    @Mock
    private HolidayService holidayService;

    private HolidayController holidayController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        holidayController = new HolidayController(messageSource, holidayService);
    }

    @Test
    void testGetHolidaysForMonth() {
        when(messageSource.getMessage("month." + CURRENT_MONTH, null, LOCALE_EN)).thenReturn(MONTH_NAME);
        when(messageSource.getMessage("holidays.message.month", new Object[]{MONTH_NAME, CURRENT_YEAR}, LOCALE_EN)).thenReturn(MONTH_MESSAGE);
        when(holidayService.getHolidaysForCurrentMonth(COUNTRY_RU)).thenReturn(HOLIDAYS_FOR_MONTH);
        ResponseEntity<HolidayListResponse> response = holidayController.getHolidaysForMonth(COUNTRY_RU, LOCALE_EN);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        HolidayListResponse body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getMessage()).isEqualTo(MONTH_MESSAGE);
        assertThat(body.getHolidays()).hasSize(2);
        assertThat(body.getHolidays()).isEqualTo(HOLIDAYS_FOR_MONTH);
    }

    @Test
    void testGetHolidaysForYear() {
        int year = 2025;
        when(messageSource.getMessage("holidays.message.year", new Object[]{year}, LOCALE_EN)).thenReturn(YEAR_MESSAGE);
        when(holidayService.getHolidaysForYear(COUNTRY_US, year)).thenReturn(HOLIDAYS_FOR_YEAR);
        ResponseEntity<HolidayListResponse> response = holidayController.getHolidaysForYear(COUNTRY_US, year, LOCALE_EN);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        HolidayListResponse body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getMessage()).isEqualTo(YEAR_MESSAGE);
        assertThat(body.getHolidays()).hasSize(1);
        assertThat(body.getHolidays()).isEqualTo(HOLIDAYS_FOR_YEAR);
    }
}
