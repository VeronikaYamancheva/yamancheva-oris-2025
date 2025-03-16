package ru.kpfu.itis.vhsroni.clinicsemestrovka.utils;

import lombok.experimental.UtilityClass;
import ru.kpfu.itis.vhsroni.clinicsemestrovka.exceptions.DateTimeValidationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@UtilityClass
public class AppointmentDateTimeValidator {

    public static final int MAX_POSSIBLE_APPOINTMENT_DATE_IN_YEARS = 1;

    public static final int MIN_START_TIME_IN_HOURS = 8;

    public static final int MAX_START_TIME_IN_HOURS = 18;

    public static void checkValidDateAndTime(LocalDate date, LocalTime time) throws DateTimeValidationException {

        LocalDate now = LocalDate.now();
        LocalDate oneYearFromNow = now.plusYears(MAX_POSSIBLE_APPOINTMENT_DATE_IN_YEARS);
        LocalTime startTime = LocalTime.of(MIN_START_TIME_IN_HOURS, 0);
        LocalTime endTime = LocalTime.of(MAX_START_TIME_IN_HOURS, 0);
        LocalDateTime nowDateTime = LocalDateTime.now();

        if (date.isAfter(oneYearFromNow)) {
            throw new DateTimeValidationException("Дата не может быть более чем через год.");
        } else if (time.isBefore(startTime) || time.isAfter(endTime)) {
            throw new DateTimeValidationException("Время приема должно быть с 8:00 до 18:00.");
        } else if (LocalDateTime.of(date, time).isBefore(nowDateTime)) {
            throw new DateTimeValidationException("Дата приема не может быть раньше текущей даты и времени.");
        }
    }
}
