package ru.itis.vhsroni.services;

import ru.itis.vhsroni.dto.response.AppointmentDetailedResponse;
import ru.itis.vhsroni.dto.response.AppointmentResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface AdminAppointmentManagementService {

    List<AppointmentResponse> findAllAppointments();

    UUID createNewAppointmentByClientAndDentistsEmails(String clientEmail, String dentistEmail, LocalDate date, LocalTime time);

    AppointmentDetailedResponse findDetailedInfoByAppointmentId(UUID appointmentId);

    List<AppointmentResponse> findAppointmentByClientEmail(String email);

    boolean updateAppointmentDateTime(LocalDate date, LocalTime time, UUID appointmentId);

    List<AppointmentDetailedResponse> findAppointmentsDetailedInfoByDateRange(LocalDate startDate, LocalDate endDate);

    void deleteAppointmentById(UUID appointmentId);
}
