package ru.itis.vhsroni.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {

    private LocalDate date;

    private LocalTime time;

    private UUID clientId;

    private UUID dentistId;
}
