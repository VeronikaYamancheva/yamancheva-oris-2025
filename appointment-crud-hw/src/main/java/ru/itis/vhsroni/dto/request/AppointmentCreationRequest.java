package ru.itis.vhsroni.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class AppointmentCreationRequest {

    private String clientEmail;

    private String dentistEmail;

    private LocalDate date;

    private LocalTime time;
}
