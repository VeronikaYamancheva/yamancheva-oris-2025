package ru.itis.vhsroni.repositories;

import ru.itis.vhsroni.dto.response.AppointmentDetailedResponse;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepositoryCustom {

    List<AppointmentDetailedResponse> findAppointmentDetailedInfoByDateRange(LocalDate startDate, LocalDate endDate);
}
