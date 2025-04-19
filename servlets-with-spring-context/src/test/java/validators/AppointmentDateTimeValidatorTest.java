package validators;

import org.junit.jupiter.api.Test;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DateTimeValidationException;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.validators.AppointmentDateTimeValidator;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentDateTimeValidatorTest {

    private static final LocalDate TOMORROW_DATE = LocalDate.now().plusDays(1);

    private static final LocalDate YESTERDAY_DATE = LocalDate.now().minusDays(1);

    private static final LocalDate IN_2_YEARS_DATE = LocalDate.now().plusYears(2);

    private static final LocalTime VALID_TIME_10_00 = LocalTime.of(10, 0);

    private static final LocalTime BEFORE_VALID_TIME_7_00 = LocalTime.of(7, 0);

    private static final LocalTime AFTER_VALID_TIME_19_00 = LocalTime.of(19, 0);

    @Test
    void testValidDateAndTime() {
        assertDoesNotThrow(() -> AppointmentDateTimeValidator.checkValidDateAndTime(TOMORROW_DATE, VALID_TIME_10_00));
    }

    @Test
    void testDateIsMoreThanOneYearFromNowAndValidTime() {
        DateTimeValidationException exception = assertThrows(DateTimeValidationException.class, () ->
                AppointmentDateTimeValidator.checkValidDateAndTime(IN_2_YEARS_DATE, VALID_TIME_10_00)
        );
        assertEquals("Дата не может быть более чем через год.", exception.getMessage());
    }

    @Test
    void testTimeIsBefore8AMAndValidDate() {
        DateTimeValidationException exception = assertThrows(DateTimeValidationException.class, () ->
                AppointmentDateTimeValidator.checkValidDateAndTime(TOMORROW_DATE, BEFORE_VALID_TIME_7_00)
        );
        assertEquals("Время приема должно быть с 8:00 до 18:00.", exception.getMessage());
    }

    @Test
    void testTimeIsAfter6PMAndValidDate() {
        DateTimeValidationException exception = assertThrows(DateTimeValidationException.class, () ->
                AppointmentDateTimeValidator.checkValidDateAndTime(TOMORROW_DATE, AFTER_VALID_TIME_19_00)
        );
        assertEquals("Время приема должно быть с 8:00 до 18:00.", exception.getMessage());
    }

    @Test
    void testDateBeforeNowAndValidTime() {
        DateTimeValidationException exception = assertThrows(DateTimeValidationException.class, () ->
                AppointmentDateTimeValidator.checkValidDateAndTime(YESTERDAY_DATE, VALID_TIME_10_00)
        );
        assertEquals("Дата приема не может быть раньше текущей даты и времени.", exception.getMessage());
    }
}
